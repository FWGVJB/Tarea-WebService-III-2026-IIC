package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.Employee;
import cr.ac.una.attendancerecorderws.model.EmployeeDtoWs;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.ResponseCode;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class EmployeeService {

    private static final Logger LOG = Logger.getLogger(EmployeeService.class.getName());

    @PersistenceContext(unitName = "AttendanceRecorderWsPU")
    private EntityManager em;

    public Response saveEmployee(EmployeeDtoWs employeeDto) {
        try {
            Employee employee = new Employee(employeeDto);
            em.persist(employee);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "Employee", new EmployeeDtoWs(employee));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al guardar el empleado.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al guardar el empleado.", "saveEmployee " + ex.getMessage());
        }
    }

    public Response updateEmployee(EmployeeDtoWs employeeDto) {
        try {
            if (employeeDto.getId() == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el empleado a modificar.", "updateEmployee Id Null");
            }
            Employee employee = em.find(Employee.class, employeeDto.getId());
            if (employee == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el empleado a modificar.", "updateEmployee NoResultException");
            }
            employee.update(employeeDto);
            employee = em.merge(employee);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "", "Employee", new EmployeeDtoWs(employee));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al actualizar el empleado.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al actualizar el empleado.", "updateEmployee " + ex.getMessage());
        }
    }

    public Response deleteEmployee(Long id) {
        try {
            Employee employee = em.find(Employee.class, id);
            if (employee == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontró el empleado a eliminar.", "deleteEmployee NoResultException");
            }
            em.remove(employee);
            em.flush();
            return new Response(true, ResponseCode.SUCCESS, "", "");
        } catch (Exception ex) {
            Throwable cause = ex;
            while (cause != null) {
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    return new Response(false, ResponseCode.INTERNAL_ERROR, "No se puede eliminar el empleado porque tiene relaciones con otros registros.", "deleteEmployee " + cause.getMessage());
                }
                cause = cause.getCause();
            }
            LOG.log(Level.SEVERE, "Ocurrió un error al eliminar el empleado.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al eliminar el empleado.", "deleteEmployee " + ex.getMessage());
        }
    }

    public Response findEmployeeById(Long id) {
        try {
            Employee employee = em.find(Employee.class, id);
            if (employee == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No existe un empleado con el código ingresado.", "findEmployeeById NoResultException");
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "Employee", new EmployeeDtoWs(employee));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar el empleado.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar el empleado.", "findEmployeeById " + ex.getMessage());
        }
    }

    public Response findAllEmployees() {
        try {
            List<Employee> employees = em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
            List<EmployeeDtoWs> employeesDto = new ArrayList<>();
            for (Employee employee : employees) {
                employeesDto.add(new EmployeeDtoWs(employee));
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "Employees", employeesDto);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar los empleados.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar los empleados.", "findAllEmployees " + ex.getMessage());
        }
    }

    public Response findEmployeeByFol(String fol) {
        try {
            Employee employee = findEmployeeByFolEntity(fol);
            if (employee == null) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No existe un empleado con el folio ingresado.", "findEmployeeByFol NoResultException");
            }
            return new Response(true, ResponseCode.SUCCESS, "", "", "Employee", new EmployeeDtoWs(employee));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar el empleado.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al consultar el empleado.", "findEmployeeByFol " + ex.getMessage());
        }
    }

    public Response authenticate(String fol, String password) {
        try {
            Employee employee = findEmployeeByFolEntity(fol);
            if (employee == null || password == null) {
                return new Response(false, ResponseCode.ACCESS_ERROR, "Credenciales inválidas.", "authenticate employee not found or password null");
            }
            boolean passwordMatches = password.equals(employee.getPassword());
            boolean isActive = "true".equals(employee.getActive());
            boolean isAdministrator = "true".equals(employee.getAdministrator());
            
            if (passwordMatches && isActive && isAdministrator) {
                return new Response(true, ResponseCode.SUCCESS, "", "", "Employee", new EmployeeDtoWs(employee));
            }
            return new Response(false, ResponseCode.ACCESS_ERROR, "Credenciales inválidas.", "authenticate invalid credentials");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al autenticar el empleado.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al autenticar el empleado.", "authenticate " + ex.getMessage());
        }
    }

    private Employee findEmployeeByFolEntity(String fol) {
        try {
            return em.createNamedQuery("Employee.findByFol", Employee.class)
                    .setParameter("fol", fol)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}