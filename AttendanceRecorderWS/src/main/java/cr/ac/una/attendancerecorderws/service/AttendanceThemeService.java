package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.AttendanceTheme;
import cr.ac.una.attendancerecorderws.model.AttendanceThemeDtoWs;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.ResponseCode;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class AttendanceThemeService {

    private static final Logger LOG = Logger.getLogger(AttendanceThemeService.class.getName());

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Response saveAttendanceTheme(AttendanceThemeDtoWs attendanceThemeDto) {
        try {
            AttendanceTheme attendanceTheme = new AttendanceTheme(attendanceThemeDto);
            em.persist(attendanceTheme);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "AttendanceTheme", new AttendanceThemeDtoWs(attendanceTheme));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al guardar el tema de asistencia.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al guardar el tema de asistencia.", "saveAttendanceTheme " + ex.getMessage());
        }
    }

    public Response updateAttendanceTheme(AttendanceThemeDtoWs attendanceThemeDto) {
        try {
            if (attendanceThemeDto.getId() == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el tema de asistencia a modificar.", "updateAttendanceTheme Id Null");
            }
            AttendanceTheme attendanceTheme = em.find(AttendanceTheme.class, attendanceThemeDto.getId());
            if (attendanceTheme == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el tema de asistencia a modificar.", "updateAttendanceTheme NoResultException");
            }
            attendanceTheme.update(attendanceThemeDto);
            attendanceTheme = em.merge(attendanceTheme);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "AttendanceTheme", new AttendanceThemeDtoWs(attendanceTheme));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al actualizar el tema de asistencia.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al actualizar el tema de asistencia.", "updateAttendanceTheme " + ex.getMessage());
        }
    }

    public Response deleteAttendanceTheme(Long id) {
        try {
            AttendanceTheme attendanceTheme = em.find(AttendanceTheme.class, id);
            if (attendanceTheme == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el tema de asistencia a eliminar.", "deleteAttendanceTheme NoResultException");
            }
            em.remove(attendanceTheme);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "");
        } catch (Exception ex) {
            Throwable cause = ex;
            while (cause != null) {
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    return new Response(false, ResponseCode.INTERNAL_ERROR, "No se puede eliminar el tema de asistencia porque tiene relaciones con otros registros.", "deleteAttendanceTheme " + cause.getMessage());
                }
                cause = cause.getCause();
            }
            LOG.log(Level.SEVERE, "Ocurrió un error al eliminar el tema de asistencia.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al eliminar el tema de asistencia.", "deleteAttendanceTheme " + ex.getMessage());
        }
    }

    public Response findAttendanceThemeById(Long id) {
        try {
            AttendanceTheme attendanceTheme = em.find(AttendanceTheme.class, id);
            if (attendanceTheme == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No existe un tema de asistencia con el código ingresado.", "findAttendanceThemeById NoResultException");
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "AttendanceTheme", new AttendanceThemeDtoWs(attendanceTheme));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar el tema de asistencia.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar el tema de asistencia.", "findAttendanceThemeById " + ex.getMessage());
        }
    }

    public Response findAllAttendanceThemes() {
        try {
            List<AttendanceTheme> themes = em.createNamedQuery("AttendanceTheme.findAll", AttendanceTheme.class).getResultList();
            List<AttendanceThemeDtoWs> themesDto = new ArrayList<>();
            for (AttendanceTheme theme : themes) {
                themesDto.add(new AttendanceThemeDtoWs(theme));
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "AttendanceThemes", themesDto);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar los temas de asistencia.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar los temas de asistencia.", "findAllAttendanceThemes " + ex.getMessage());
        }
    }

    public Response findAttendanceThemesByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            List<AttendanceTheme> themes = em.createNamedQuery("AttendanceTheme.findByDateRange", AttendanceTheme.class)
                    .setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
            List<AttendanceThemeDtoWs> themesDto = new ArrayList<>();
            for (AttendanceTheme theme : themes) {
                themesDto.add(new AttendanceThemeDtoWs(theme));
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "AttendanceThemes", themesDto);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar los temas de asistencia por rango de fechas.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar los temas de asistencia por rango de fechas.", "findAttendanceThemesByDateRange " + ex.getMessage());
        }
    }

    public Response findAttendanceThemeByDate(LocalDate date) {
        try {
            List<AttendanceTheme> themes = em.createNamedQuery("AttendanceTheme.findByDate", AttendanceTheme.class)
                    .setParameter("date", date).getResultList();
            if (themes == null || themes.isEmpty()) {
                return new Response(true, ResponseCode.SUCCESS, "", "", "AttendanceTheme", null);
            }
            AttendanceTheme theme = themes.get(0);
            return new Response(true, ResponseCode.SUCCESS, "", "", "AttendanceTheme", new AttendanceThemeDtoWs(theme));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar el tema de asistencia por fecha.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar el tema de asistencia por fecha.", "findAttendanceThemeByDate " + ex.getMessage());
        }
    }
}