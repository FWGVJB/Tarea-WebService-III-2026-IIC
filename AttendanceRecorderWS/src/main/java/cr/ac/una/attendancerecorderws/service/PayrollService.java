package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.Payroll;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PayrollService {

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Payroll savePayroll(Payroll payroll) {
        em.persist(payroll);
        em.flush();
        em.refresh(payroll);
        
        if (payroll.getDetails() != null) {
            payroll.getDetails().size();
        }
        
        return payroll;
    }

    public Payroll updatePayroll(Payroll payroll) {
        payroll = em.merge(payroll);
        em.flush();
        
        if (payroll.getDetails() != null) {
            payroll.getDetails().size();
        }
        
        return payroll;
    }

    public void deletePayroll(Long id) {
        Payroll payroll = em.find(Payroll.class, id);
        if (payroll != null) {
            em.remove(payroll);
        }
    }

    public Payroll findPayrollById(Long id) {
        try {
            Payroll p = em.find(Payroll.class, id);
            if (p != null && p.getDetails() != null) {
                // Se fuerza la carga de los detalles (debido al Lazy Loading) para enviarlos completos al cliente y evitar un LazyInitializationException
                p.getDetails().size();
            }
            return p;
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Payroll> findAllPayrolls() {
        List<Payroll> list = em.createNamedQuery("Payroll.findAll", Payroll.class).getResultList();
        for (Payroll p : list) {
            if (p.getDetails() != null) {
                 // Se fuerza la carga de los detalles (debido al Lazy Loading) para enviarlos completos al cliente y evitar un LazyInitializationException
                p.getDetails().size();
            }
        }

        return list;
    }
}