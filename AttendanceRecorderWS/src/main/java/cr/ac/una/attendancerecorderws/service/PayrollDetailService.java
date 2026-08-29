package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.PayrollDetail;
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
public class PayrollDetailService {

    private static final Logger LOG = Logger.getLogger(PayrollDetailService.class.getName());

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Response savePayrollDetail(PayrollDetail payrollDetail) {
        try {
            em.persist(payrollDetail);
            em.flush();
            em.refresh(payrollDetail);
            return new Response(true, ResponseCode.SUCCESS, "", "", "PayrollDetail", payrollDetail);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al guardar el detalle de planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al guardar el detalle de planilla.", "savePayrollDetail " + ex.getMessage());
        }
    }

    public Response updatePayrollDetail(PayrollDetail payrollDetail) {
        try {
            if (payrollDetail.getId() == null || em.find(PayrollDetail.class, payrollDetail.getId()) == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el detalle de planilla a modificar.", "updatePayrollDetail NoResultException");
            }
            payrollDetail = em.merge(payrollDetail);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "PayrollDetail", payrollDetail);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al actualizar el detalle de planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al actualizar el detalle de planilla.", "updatePayrollDetail " + ex.getMessage());
        }
    }

    public Response deletePayrollDetail(Long id) {
        try {
            PayrollDetail payrollDetail = em.find(PayrollDetail.class, id);
            if (payrollDetail == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el detalle de planilla a eliminar.", "deletePayrollDetail NoResultException");
            }
            em.remove(payrollDetail);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "");
        } catch (Exception ex) {
            Throwable cause = ex;
            while (cause != null) {
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    return new Response(false, ResponseCode.INTERNAL_ERROR, "No se puede eliminar el detalle de planilla porque tiene relaciones con otros registros.", "deletePayrollDetail " + cause.getMessage());
                }
                cause = cause.getCause();
            }
            LOG.log(Level.SEVERE, "Ocurrió un error al eliminar el detalle de planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al eliminar el detalle de planilla.", "deletePayrollDetail " + ex.getMessage());
        }
    }

    public Response findPayrollDetailById(Long id) {
        try {
            PayrollDetail payrollDetail = em.find(PayrollDetail.class, id);
            if (payrollDetail == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No existe un detalle de planilla con el código ingresado.", "findPayrollDetailById NoResultException");
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "PayrollDetail", payrollDetail);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar el detalle de planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar el detalle de planilla.", "findPayrollDetailById " + ex.getMessage());
        }
    }

    public Response findAllPayrollDetails() {
        try {
            List<PayrollDetail> payrollDetails = em.createNamedQuery("PayrollDetail.findAll", PayrollDetail.class).getResultList();
            return new Response(true, ResponseCode.SUCCESS, "", "", "PayrollDetails", payrollDetails);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar los detalles de planilla.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar los detalles de planilla.", "findAllPayrollDetails " + ex.getMessage());
        }
    }
}
