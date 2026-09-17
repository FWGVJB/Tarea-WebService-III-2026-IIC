package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.Employee;
import cr.ac.una.attendancerecorderws.model.Payroll;
import cr.ac.una.attendancerecorderws.model.PayrollDetail;
import cr.ac.una.attendancerecorderws.model.PayrollDetailDtoWs;
import cr.ac.una.attendancerecorderws.model.PayrollDtoWs;
import cr.ac.una.attendancerecorderws.model.Shift;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.ResponseCode;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class PayrollService {

    private static final Logger LOG = Logger.getLogger(PayrollService.class.getName());

    private static final double MAX_DAY_ORDINARY_HOURS = 8.0, MAX_NIGHT_ORDINARY_HOURS = 6.0;
    private static final double NIGHT_MULTIPLIER = 4.0 / 3.0, EXTRA_MULTIPLIER = 1.5, DOUBLE_MULTIPLIER = 2.0;
    private static final LocalTime DAY_SHIFT_START = LocalTime.of(2, 0), DAY_SHIFT_END = LocalTime.of(22, 0);

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Response savePayroll(PayrollDtoWs payrollDto) {
        try {
            Payroll payroll = new Payroll(payrollDto);

            if (payroll.getDetails() != null) {
                for (PayrollDetail detail : payroll.getDetails()) {
                    if (detail.getEmployee() != null && detail.getEmployee().getId() != null) {
                        Employee realEmployee = em.find(Employee.class, detail.getEmployee().getId());
                        detail.setEmployee(realEmployee);
                    }
                }
            }

            em.persist(payroll);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "Payroll", convertToDtoWithDetails(payroll));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al guardar la planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al guardar la planilla.", "savePayroll " + ex.getMessage());
        }
    }

    public Response generatePayroll(Integer month, Integer year) {
        try {
            List<Employee> employees = em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
            if (employees.isEmpty()) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No hay empleados para generar la planilla.", "");
            }

            List<Payroll> existingPayrolls = em.createQuery("SELECT p FROM Payroll p WHERE p.month = :month AND p.year = :year", Payroll.class)
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .getResultList();

            Payroll payroll;
            boolean isNew = false;
            if (!existingPayrolls.isEmpty()) {
                payroll = existingPayrolls.get(0);
                if (payroll.getDetails() != null) {
                    for (PayrollDetail oldDetail : payroll.getDetails()) {
                        em.remove(oldDetail);
                    }
                    payroll.getDetails().clear();
                }
            } else {
                payroll = new Payroll();
                payroll.setMonth(month);
                payroll.setYear(year);
                isNew = true;
            }

            double totalPaymentSum = 0.0;
            List<PayrollDetail> details = new ArrayList<>();

            for (Employee employee : employees) {
                PayrollDetail detail = new PayrollDetail();
                detail.setEmployee(employee);
                detail.setPayroll(payroll);

                double hourlyWage = employee.getHourlyWage() != null ? employee.getHourlyWage() : 0.0;
                detail.setHourlyWage(hourlyWage);

                double ordinaryHours = 0.0;
                double extraHours = 0.0;
                double doubleHours = 0.0;

                if (employee.getShifts() != null) {
                    for (Shift shift : employee.getShifts()) {
                        if (shift.getEntryRecord() != null && shift.getExitRecord() != null) {
                            LocalDateTime entry = shift.getEntryRecord().getTimestamp();
                            LocalDateTime exit = shift.getExitRecord().getTimestamp();

                            if (entry.getMonthValue() == month && entry.getYear() == year) {
                                double shiftHours = getHoursInPeriod(entry, exit);
                                if (shiftHours <= 0) {
                                    continue;
                                }

                                boolean isNightShift = isNightShift(entry, exit);
                                double maxOrdinary = isNightShift ? MAX_NIGHT_ORDINARY_HOURS : MAX_DAY_ORDINARY_HOURS;
                                double nightMultiplier = isNightShift ? NIGHT_MULTIPLIER : 1.0;

                                double ordinary = Math.min(shiftHours, maxOrdinary) * nightMultiplier;
                                double extra = Math.max(0.0, shiftHours - maxOrdinary) * nightMultiplier * EXTRA_MULTIPLIER;

                                double sundayHours = getSundayHours(entry, exit);
                                if (sundayHours > 0 && shiftHours > 0) {
                                    double sundayFraction = sundayHours / shiftHours;
                                    doubleHours += (ordinary + extra) * sundayFraction;
                                    ordinaryHours += ordinary * (1.0 - sundayFraction);
                                    extraHours += extra * (1.0 - sundayFraction);
                                } else {
                                    ordinaryHours += ordinary;
                                    extraHours += extra;
                                }
                            }
                        }
                    }
                }

                ordinaryHours = roundHours(ordinaryHours);
                extraHours = roundHours(extraHours);
                doubleHours = roundHours(doubleHours);

                detail.setOrdinaryHours(ordinaryHours);
                detail.setExtraHours(extraHours);
                detail.setDoubleHours(doubleHours);

                double monthlySalary = (ordinaryHours + extraHours + doubleHours * 2.0) * hourlyWage;
                detail.setMonthlySalary(monthlySalary);

                details.add(detail);
                totalPaymentSum += monthlySalary;
            }

            payroll.setDetails(details);
            payroll.setTotalPayment(totalPaymentSum);

            if (isNew) {
                em.persist(payroll);
            } else {
                payroll = em.merge(payroll);
            }
            em.flush();

            return new Response(true, ResponseCode.SUCCESS, "", "", "Payroll", convertToDtoWithDetails(payroll));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al generar la planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Error al generar la planilla.", ex.getMessage());
        }
    }

    public Response updatePayroll(PayrollDtoWs payrollDto) {
        try {
            if (payrollDto.getId() == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró la planilla a modificar.", "updatePayroll Id Null");
            }
            Payroll payroll = em.find(Payroll.class, payrollDto.getId());
            if (payroll == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró la planilla a modificar.", "updatePayroll NoResultException");
            }

            List<PayrollDetail> detailsToRemove = new ArrayList<>();
            if (payroll.getDetails() != null) {
                for (PayrollDetail detail : payroll.getDetails()) {
                    boolean exists = false;
                    if (payrollDto.getDetails() != null) {
                        for (PayrollDetailDtoWs dtoDetail : payrollDto.getDetails()) {
                            if (detail.getId() != null && detail.getId().equals(dtoDetail.getId())) {
                                exists = true;
                                break;
                            }
                        }
                    }
                    if (!exists) {
                        detailsToRemove.add(detail);
                    }
                }
            }

            for (PayrollDetail detail : detailsToRemove) {
                payroll.getDetails().remove(detail);
                em.remove(detail);
            }

            payroll.update(payrollDto);

            if (payroll.getDetails() != null) {
                for (PayrollDetail detail : payroll.getDetails()) {
                    if (detail.getEmployee() != null && detail.getEmployee().getId() != null) {
                        Employee realEmployee = em.find(Employee.class, detail.getEmployee().getId());
                        detail.setEmployee(realEmployee);
                    }
                }
            }

            payroll = em.merge(payroll);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "Payroll", convertToDtoWithDetails(payroll));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al actualizar la planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al actualizar la planilla.", "updatePayroll " + ex.getMessage());
        }
    }

    public Response deletePayroll(Long id) {
        try {
            Payroll payroll = em.find(Payroll.class, id);
            if (payroll == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró la planilla a eliminar.", "deletePayroll NoResultException");
            }
            em.remove(payroll);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "");
        } catch (Exception ex) {
            Throwable cause = ex;
            while (cause != null) {
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    return new Response(false, ResponseCode.INTERNAL_ERROR, "No se puede eliminar la planilla porque tiene relaciones con otros registros.", "deletePayroll " + cause.getMessage());
                }
                cause = cause.getCause();
            }
            LOG.log(Level.SEVERE, "Ocurrió un error al eliminar la planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al eliminar la planilla.", "deletePayroll " + ex.getMessage());
        }
    }

    public Response findPayrollById(Long id) {
        try {
            Payroll payroll = em.find(Payroll.class, id);
            if (payroll == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No existe una planilla con el código ingresado.", "findPayrollById NoResultException");
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "Payroll", convertToDtoWithDetails(payroll));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar la planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar la planilla.", "findPayrollById " + ex.getMessage());
        }
    }

    public Response findAllPayrolls() {
        try {
            List<Payroll> payrolls = em.createNamedQuery("Payroll.findAll", Payroll.class).getResultList();
            List<PayrollDtoWs> payrollsDto = new ArrayList<>();
            for (Payroll payroll : payrolls) {
                payrollsDto.add(convertToDtoWithDetails(payroll));
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "Payrolls", payrollsDto);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar las planillas.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar las planillas.", "findAllPayrolls " + ex.getMessage());
        }
    }

    private PayrollDtoWs convertToDtoWithDetails(Payroll payroll) {
        PayrollDtoWs dto = new PayrollDtoWs(payroll);
        if (payroll.getDetails() != null) {
            for (PayrollDetail detail : payroll.getDetails()) {
                dto.getDetails().add(new PayrollDetailDtoWs(detail));
            }
        }
        return dto;
    }

    private double getHoursInPeriod(LocalDateTime entry, LocalDateTime exit) {
        long minutes = Duration.between(entry, exit).toMinutes();
        int blocksOf30Minutes = Math.round(minutes / 30.0f);
        return blocksOf30Minutes / 2.0;
    }

    private double getSundayHours(LocalDateTime entry, LocalDateTime exit) {
        LocalDateTime sundayStart = entry.getDayOfWeek() == DayOfWeek.SUNDAY ? entry.toLocalDate().atStartOfDay() : entry.toLocalDate().plusDays(1).atStartOfDay();
        LocalDateTime sundayEnd = sundayStart.plusDays(1);

        LocalDateTime overlapStart = entry.isAfter(sundayStart) ? entry : sundayStart;
        LocalDateTime overlapEnd = exit.isBefore(sundayEnd) ? exit : sundayEnd;

        long sundayMinutes = 0;
        if (overlapStart.isBefore(overlapEnd)) {
            sundayMinutes = Duration.between(overlapStart, overlapEnd).toMinutes();
        }

        int blocksOf30Minutes = Math.round(sundayMinutes / 30.0f);
        return blocksOf30Minutes / 2.0;
    }

    private boolean isNightShift(LocalDateTime entry, LocalDateTime exit) {
        boolean startsBeforeDayShift = entry.toLocalTime().isBefore(DAY_SHIFT_START);
        boolean endsAfterDayShift = exit.toLocalTime().isAfter(DAY_SHIFT_END);
        return startsBeforeDayShift || endsAfterDayShift;
    }
    
    private double roundHours(double hours) {
        return Math.round(hours * 10000.0) / 10000.0;
    }
    
}