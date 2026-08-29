package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.TimeRecord;
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
public class TimeRecordService {

    private static final Logger LOG = Logger.getLogger(TimeRecordService.class.getName());

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Response saveTimeRecord(TimeRecord timeRecord) {
        try {
            em.persist(timeRecord);
            em.flush();
            em.refresh(timeRecord);
            return new Response(true, ResponseCode.SUCCESS, "", "", "TimeRecord", timeRecord);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al guardar la marca.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al guardar la marca.", "saveTimeRecord " + ex.getMessage());
        }
    }

    public Response updateTimeRecord(TimeRecord timeRecord) {
        try {
            if (timeRecord.getId() == null || em.find(TimeRecord.class, timeRecord.getId()) == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró la marca a modificar.", "updateTimeRecord NoResultException");
            }
            timeRecord = em.merge(timeRecord);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "TimeRecord", timeRecord);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al actualizar la marca.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al actualizar la marca.", "updateTimeRecord " + ex.getMessage());
        }
    }

    public Response deleteTimeRecord(Long id) {
        try {
            TimeRecord timeRecord = em.find(TimeRecord.class, id);
            if (timeRecord == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró la marca a eliminar.", "deleteTimeRecord NoResultException");
            }
            em.remove(timeRecord);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "");
        } catch (Exception ex) {
            Throwable cause = ex;
            while (cause != null) {
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    return new Response(false, ResponseCode.INTERNAL_ERROR, "No se puede eliminar la marca porque tiene relaciones con otros registros.", "deleteTimeRecord " + cause.getMessage());
                }
                cause = cause.getCause();
            }
            LOG.log(Level.SEVERE, "Ocurrió un error al eliminar la marca.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al eliminar la marca.", "deleteTimeRecord " + ex.getMessage());
        }
    }

    public Response findTimeRecordById(Long id) {
        try {
            TimeRecord timeRecord = em.find(TimeRecord.class, id);
            if (timeRecord == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No existe una marca con el código ingresado.", "findTimeRecordById NoResultException");
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "TimeRecord", timeRecord);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar la marca.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar la marca.", "findTimeRecordById " + ex.getMessage());
        }
    }

    public Response findAllTimeRecords() {
        try {
            List<TimeRecord> timeRecords = em.createNamedQuery("TimeRecord.findAll", TimeRecord.class).getResultList();
            return new Response(true, ResponseCode.SUCCESS, "", "", "TimeRecords", timeRecords);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar las marcas.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar las marcas.", "findAllTimeRecords " + ex.getMessage());
        }
    }
}
