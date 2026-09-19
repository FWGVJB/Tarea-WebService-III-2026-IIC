package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.EmployeeDtoWs;
import cr.ac.una.attendancerecorderws.model.PayrollDtoWs;
import cr.ac.una.attendancerecorderws.model.ShiftDtoWs;
import cr.ac.una.attendancerecorderws.util.JasperGenerator;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;

@Stateless
@LocalBean
public class JasperService {
    
    private static final Logger LOG = Logger.getLogger(JasperService.class.getName());

    @EJB private EmployeeService employeeService;
    @EJB private ShiftService shiftService;
    @EJB private PayrollService payrollService;
    @EJB private JasperGenerator jasperGenerator;
    
    public Response generateEmployeeInformationReport(List<Long> idList) {
        try {
            List<EmployeeDtoWs> employees = new ArrayList<>();
            for (Long id : idList) {
                Response employeeResponse = employeeService.findEmployeeById(id);
                if (Boolean.TRUE.equals(employeeResponse.getStatus())) {
                    EmployeeDtoWs employee = (EmployeeDtoWs) employeeResponse.getResult("Employee");
                    employees.add(employee);
                }
            }
            if (employees.isEmpty()) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontraron empleados con los IDs ingresados.", "generateEmployeeInformationReport no employees found");
            }
            byte[] pdfBytes = jasperGenerator.generateEmployeeInformationReport(employees);
            return new Response(true, ResponseCode.SUCCESS, "", "", "PdfReport", pdfBytes);
        } catch (JRException ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al generar el reporte de empleados.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al generar el reporte de empleados.", "generateEmployeeInformationReport " + ex.getMessage());
        }
    }

    public Response generateShiftReport(LocalDate startDate, LocalDate endDate, List<Long> idList, int timeRecordsAmount, int employeesAmount, double totalHours, String types) {
        try {
            List<ShiftDtoWs> shifts = new ArrayList<>();
            for (Long id : idList) {
                Response shiftResponse = shiftService.findShiftById(id);
                if (Boolean.TRUE.equals(shiftResponse.getStatus())) {
                    ShiftDtoWs shift = (ShiftDtoWs) shiftResponse.getResult("Shift");
                    shifts.add(shift);
                }
            }
            if (shifts.isEmpty()) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontraron turnos con los IDs ingresados.", "generateShiftReport no shifts found");
            }
            byte[] pdfBytes = jasperGenerator.generateShiftReport(shifts, startDate, endDate, timeRecordsAmount, employeesAmount, totalHours, types);
            return new Response(true, ResponseCode.SUCCESS, "", "", "PdfReport", pdfBytes);
        } catch (JRException ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al generar el reporte de marcas.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR,
                    "Ocurrió un error al generar el reporte de marcas.",
                    "generateShiftReport " + ex.getMessage());
        }
    }
    
    public Response generatePayrollReport(Long payrollId) {
        try {
            Response payrollResponse = payrollService.findPayrollById(payrollId);
            if (!Boolean.TRUE.equals(payrollResponse.getStatus())) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró la planilla con el ID ingresado.",
                    "generatePayrollReport payroll not found");
            }

            PayrollDtoWs payroll = (PayrollDtoWs) payrollResponse.getResult("Payroll");
            byte[] pdfBytes = jasperGenerator.generatePayrollReport(payroll);
            return new Response(true, ResponseCode.SUCCESS, "", "", "PdfReport", pdfBytes);
        } catch (JRException ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al generar el reporte de planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al generar el reporte de planilla.",
                "generatePayrollReport " + ex.getMessage());
        }
    }
    
}
