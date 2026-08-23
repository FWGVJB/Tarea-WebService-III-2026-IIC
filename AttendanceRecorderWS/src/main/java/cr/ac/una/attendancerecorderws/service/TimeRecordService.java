package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.TimeRecord;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TimeRecordService {

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public TimeRecord saveTimeRecord(TimeRecord timeRecord) {
        em.persist(timeRecord);
        em.flush();
        em.refresh(timeRecord); 
        return timeRecord;
    }

    public TimeRecord updateTimeRecord(TimeRecord timeRecord) {
        timeRecord = em.merge(timeRecord);
        em.flush();
        return timeRecord;
    }

    public void deleteTimeRecord(Long id) {
        TimeRecord timeRecord = em.find(TimeRecord.class, id);
        if (timeRecord != null) {
            em.remove(timeRecord);
        }
    }

    public TimeRecord findTimeRecordById(Long id) {
        try {
            return em.find(TimeRecord.class, id);
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<TimeRecord> findAllTimeRecords() {
        return em.createNamedQuery("TimeRecord.findAll", TimeRecord.class).getResultList();
    }
}