package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.Employee;
import cr.ac.una.attendancerecorderws.model.EmployeeDtoWs;
import cr.ac.una.attendancerecorderws.service.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

@WebService(serviceName = "EmployeeWS")
public class EmployeeWS {

    @EJB
    private EmployeeService employeeService;

    @WebMethod(operationName = "saveEmployee")
    public EmployeeDtoWs saveEmployee(@WebParam(name = "employee") EmployeeDtoWs employeeDto) {
        Employee employee = new Employee(employeeDto);
        Employee saved = employeeService.saveEmployee(employee);
        return new EmployeeDtoWs(saved);
    }

    @WebMethod(operationName = "updateEmployee")
    public EmployeeDtoWs updateEmployee(@WebParam(name = "employee") EmployeeDtoWs employeeDto) {
        Employee employee = new Employee(employeeDto);
        Employee updated = employeeService.updateEmployee(employee);
        return new EmployeeDtoWs(updated);
    }

    @WebMethod(operationName = "deleteEmployee")
    public void deleteEmployee(@WebParam(name = "id") Long id) {
        employeeService.deleteEmployee(id);
    }

    @WebMethod(operationName = "findEmployeeById")
    public EmployeeDtoWs findEmployeeById(@WebParam(name = "id") Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        return employee != null ? new EmployeeDtoWs(employee) : null;
    }

    @WebMethod(operationName = "findAllEmployees")
    public List<EmployeeDtoWs> findAllEmployees() {
        return employeeService.findAllEmployees()
                .stream()
                .map(EmployeeDtoWs::new)
                .collect(Collectors.toList());
    }

    @WebMethod(operationName = "findEmployeeByFol")
    public EmployeeDtoWs findEmployeeByFol(@WebParam(name = "fol") String fol) {
        Employee employee = employeeService.findEmployeeByFol(fol);
        return employee != null ? new EmployeeDtoWs(employee) : null;
    }

    /**
     * Login de administradores
     * Retorna null si folio/clave no coinciden, el empleado esta inactivo, o no es administrador
     */
    @WebMethod(operationName = "authenticate")
    public EmployeeDtoWs authenticate(@WebParam(name = "fol") String fol, @WebParam(name = "password") String password) {
        Employee employee = employeeService.authenticate(fol, password);
        return employee != null ? new EmployeeDtoWs(employee) : null;
    }
}