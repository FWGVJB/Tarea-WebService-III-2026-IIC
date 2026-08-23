package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.Shift;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ShiftService {

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Shift saveShift(Shift shift) {
        em.persist(shift);
        em.flush();
        em.refresh(shift);
        return shift;
    }

    public Shift updateShift(Shift shift) {
        if (shift.getId() != null) {
            Shift existingShift = em.find(Shift.class, shift.getId());
            if (existingShift != null) {
                shift.setShiftReport(existingShift.getShiftReport());
            }
        }
        
        shift = em.merge(shift);
        em.flush();
        return shift;
    }

    public void deleteShift(Long id) {
        Shift shift = em.find(Shift.class, id);
        if (shift != null) {
            em.remove(shift);
        }
    }

    public Shift findShiftById(Long id) {
        return em.find(Shift.class, id);
    }

    public List<Shift> findAllShifts() {
        return em.createNamedQuery("Shift.findAll", Shift.class).getResultList();
    }
}