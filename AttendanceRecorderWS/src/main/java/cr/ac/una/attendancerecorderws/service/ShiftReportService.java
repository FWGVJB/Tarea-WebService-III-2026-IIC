package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.Shift;
import cr.ac.una.attendancerecorderws.model.ShiftDtoWs;
import cr.ac.una.attendancerecorderws.model.ShiftReport;
import cr.ac.una.attendancerecorderws.model.ShiftReportDtoWs;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.ResponseCode;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ShiftReportService {

    private static final Logger LOG = Logger.getLogger(ShiftReportService.class.getName());

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Response saveShiftReport(ShiftReportDtoWs shiftReportDto) {
        try {
            ShiftReport shiftReport = new ShiftReport(shiftReportDto);
            em.persist(shiftReport);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "ShiftReport", convertToDtoWithDetails(shiftReport));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al guardar el reporte de turnos.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al guardar el reporte de turnos.", "saveShiftReport " + ex.getMessage());
        }
    }

    public Response updateShiftReport(ShiftReportDtoWs shiftReportDto) {
        try {
            if (shiftReportDto.getId() == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el reporte de turnos a modificar.", "updateShiftReport Id Null");
            }
            ShiftReport shiftReport = em.find(ShiftReport.class, shiftReportDto.getId());
            if (shiftReport == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el reporte de turnos a modificar.", "updateShiftReport NoResultException");
            }
            shiftReport.update(shiftReportDto);
            shiftReport = em.merge(shiftReport);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "ShiftReport", convertToDtoWithDetails(shiftReport));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al actualizar el reporte de turnos.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al actualizar el reporte de turnos.", "updateShiftReport " + ex.getMessage());
        }
    }

    public Response deleteShiftReport(Long id) {
        try {
            ShiftReport shiftReport = em.find(ShiftReport.class, id);
            if (shiftReport == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el reporte de turnos a eliminar.", "deleteShiftReport NoResultException");
            }
            em.remove(shiftReport);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "");
        } catch (Exception ex) {
            Throwable cause = ex;
            while (cause != null) {
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    return new Response(false, ResponseCode.INTERNAL_ERROR, "No se puede eliminar el reporte de turnos porque tiene relaciones con otros registros.", "deleteShiftReport " + cause.getMessage());
                }
                cause = cause.getCause();
            }
            LOG.log(Level.SEVERE, "Ocurrió un error al eliminar el reporte de turnos.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al eliminar el reporte de turnos.", "deleteShiftReport " + ex.getMessage());
        }
    }

    public Response findShiftReportById(Long id) {
        try {
            ShiftReport shiftReport = em.find(ShiftReport.class, id);
            if (shiftReport == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No existe un reporte de turnos con el código ingresado.", "findShiftReportById NoResultException");
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "ShiftReport", convertToDtoWithDetails(shiftReport));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar el reporte de turnos.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar el reporte de turnos.", "findShiftReportById " + ex.getMessage());
        }
    }

    public Response findAllShiftReports() {
        try {
            List<ShiftReport> shiftReports = em.createNamedQuery("ShiftReport.findAll", ShiftReport.class).getResultList();
            List<ShiftReportDtoWs> shiftReportsDto = new ArrayList<>();
            for (ShiftReport shiftReport : shiftReports) {
                shiftReportsDto.add(convertToDtoWithDetails(shiftReport));
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "ShiftReports", shiftReportsDto);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar los reportes de turnos.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar los reportes de turnos.", "findAllShiftReports " + ex.getMessage());
        }
    }

    private ShiftReportDtoWs convertToDtoWithDetails(ShiftReport shiftReport) {
        ShiftReportDtoWs dto = new ShiftReportDtoWs(shiftReport);
        if (shiftReport.getShifts() != null) {
            if (dto.getShifts() == null) {
                dto.setShifts(new ArrayList<>());
            }
            for (Shift shift : shiftReport.getShifts()) {
                dto.getShifts().add(new ShiftDtoWs(shift));
            }
        }
        return dto;
    }
}