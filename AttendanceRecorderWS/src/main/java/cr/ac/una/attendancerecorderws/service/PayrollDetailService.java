package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.PayrollDetail;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PayrollDetailService {

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public PayrollDetail savePayrollDetail(PayrollDetail payrollDetail) {
        em.persist(payrollDetail);
        em.flush();
        em.refresh(payrollDetail);
        return payrollDetail;
    }

    public PayrollDetail updatePayrollDetail(PayrollDetail payrollDetail) {
        payrollDetail = em.merge(payrollDetail);
        em.flush();
        return payrollDetail;
    }

    public void deletePayrollDetail(Long id) {
        PayrollDetail payrollDetail = em.find(PayrollDetail.class, id);
        if (payrollDetail != null) {
            em.remove(payrollDetail);
        }
    }

    public PayrollDetail findPayrollDetailById(Long id) {
        try {
            return em.find(PayrollDetail.class, id);
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<PayrollDetail> findAllPayrollDetails() {
        return em.createNamedQuery("PayrollDetail.findAll", PayrollDetail.class).getResultList();
    }
}