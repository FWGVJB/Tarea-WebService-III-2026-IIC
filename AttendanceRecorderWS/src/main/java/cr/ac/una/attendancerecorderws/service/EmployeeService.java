package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.Employee;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EmployeeService {

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Employee saveEmployee(Employee employee) {
        em.persist(employee);
        em.flush();
        return employee;
    }

    public Employee updateEmployee(Employee employee) {
        return em.merge(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = em.find(Employee.class, id);
        if (employee != null) {
            em.remove(employee);
        }
    }

    public Employee findEmployeeById(Long id) {
        return em.find(Employee.class, id);
    }

    public List<Employee> findAllEmployees() {
        return em.createNamedQuery("Employee.findAll", Employee.class)
                .getResultList();
    }

    public Employee findEmployeeByFol(String fol) {
        try {
            return em.createNamedQuery("Employee.findByFol", Employee.class)
                    .setParameter("fol", fol)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}