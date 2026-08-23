package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.ShiftReport;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ShiftReportService {

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public ShiftReport saveShiftReport(ShiftReport shiftReport) {
        em.persist(shiftReport);
        em.flush();
        em.refresh(shiftReport);
        
        if (shiftReport.getShifts() != null) {
            shiftReport.getShifts().size();
        }
        
        return shiftReport;
    }

    public ShiftReport updateShiftReport(ShiftReport shiftReport) {
        shiftReport = em.merge(shiftReport);
        em.flush();

        if (shiftReport.getShifts() != null) {
            shiftReport.getShifts().size();
        }
        
        return shiftReport;
    }

    public void deleteShiftReport(Long id) {
        ShiftReport shiftReport = em.find(ShiftReport.class, id);
        if (shiftReport != null) {
            em.remove(shiftReport);
        }
    }

    public ShiftReport findShiftReportById(Long id) {
        try {
            ShiftReport shiftReport = em.find(ShiftReport.class, id);
            
            if (shiftReport != null && shiftReport.getShifts() != null) {
                shiftReport.getShifts().size();
            }
            
            return shiftReport;
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<ShiftReport> findAllShiftReports() {
        List<ShiftReport> shiftReports = em.createNamedQuery("ShiftReport.findAll", ShiftReport.class).getResultList();
        
        for (ShiftReport shiftReport : shiftReports) {
            if (shiftReport.getShifts() != null) {
                shiftReport.getShifts().size();
            }
        }
        
        return shiftReports;
    }
}