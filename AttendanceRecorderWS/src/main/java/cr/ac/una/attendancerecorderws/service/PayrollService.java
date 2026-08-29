package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.Payroll;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.ResponseCode;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class PayrollService {

    private static final Logger LOG = Logger.getLogger(PayrollService.class.getName());

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Response savePayroll(Payroll payroll) {
        try {
            em.persist(payroll);
            em.flush();
            em.refresh(payroll);
            if (payroll.getDetails() != null) {
                payroll.getDetails().size();
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "Payroll", payroll);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al guardar la planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al guardar la planilla.", "savePayroll " + ex.getMessage());
        }
    }

    public Response updatePayroll(Payroll payroll) {
        try {
            if (payroll.getId() == null || em.find(Payroll.class, payroll.getId()) == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró la planilla a modificar.", "updatePayroll NoResultException");
            }
            payroll = em.merge(payroll);
            em.flush();
            if (payroll.getDetails() != null) {
                payroll.getDetails().size();
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "Payroll", payroll);
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
            if (payroll.getDetails() != null) {
                payroll.getDetails().size();
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "Payroll", payroll);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar la planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar la planilla.", "findPayrollById " + ex.getMessage());
        }
    }

    public Response findAllPayrolls() {
        try {
            List<Payroll> payrolls = em.createNamedQuery("Payroll.findAll", Payroll.class).getResultList();
            for (Payroll payroll : payrolls) {
                if (payroll.getDetails() != null) {
                    payroll.getDetails().size();
                }
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "Payrolls", payrolls);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar las planillas.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar las planillas.", "findAllPayrolls " + ex.getMessage());
        }
    }
}
