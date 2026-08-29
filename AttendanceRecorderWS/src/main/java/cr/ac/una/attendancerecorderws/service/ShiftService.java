package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.Shift;
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
public class ShiftService {

    private static final Logger LOG = Logger.getLogger(ShiftService.class.getName());

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Response saveShift(Shift shift) {
        try {
            em.persist(shift);
            em.flush();
            em.refresh(shift);
            return new Response(true, ResponseCode.SUCCESS, "", "", "Shift", shift);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al guardar el turno.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al guardar el turno.", "saveShift " + ex.getMessage());
        }
    }

    public Response updateShift(Shift shift) {
        try {
            if (shift.getId() != null) {
                Shift existingShift = em.find(Shift.class, shift.getId());
                if (existingShift == null) {
                    return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el turno a modificar.", "updateShift NoResultException");
                }
                shift.setShiftReport(existingShift.getShiftReport());
            }
            shift = em.merge(shift);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "Shift", shift);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al actualizar el turno.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al actualizar el turno.", "updateShift " + ex.getMessage());
        }
    }

    public Response deleteShift(Long id) {
        try {
            Shift shift = em.find(Shift.class, id);
            if (shift == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el turno a eliminar.", "deleteShift NoResultException");
            }
            em.remove(shift);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "");
        } catch (Exception ex) {
            Throwable cause = ex;
            while (cause != null) {
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    return new Response(false, ResponseCode.INTERNAL_ERROR, "No se puede eliminar el turno porque tiene relaciones con otros registros.", "deleteShift " + cause.getMessage());
                }
                cause = cause.getCause();
            }
            LOG.log(Level.SEVERE, "Ocurrió un error al eliminar el turno.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al eliminar el turno.", "deleteShift " + ex.getMessage());
        }
    }

    public Response findShiftById(Long id) {
        try {
            Shift shift = em.find(Shift.class, id);
            if (shift == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No existe un turno con el código ingresado.", "findShiftById NoResultException");
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "Shift", shift);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar el turno.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar el turno.", "findShiftById " + ex.getMessage());
        }
    }

    public Response findAllShifts() {
        try {
            List<Shift> shifts = em.createNamedQuery("Shift.findAll", Shift.class).getResultList();
            return new Response(true, ResponseCode.SUCCESS, "", "", "Shifts", shifts);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar los turnos.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar los turnos.", "findAllShifts " + ex.getMessage());
        }
    }
}
