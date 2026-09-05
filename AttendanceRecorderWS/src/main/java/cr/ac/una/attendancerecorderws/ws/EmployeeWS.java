package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.Employee;
import cr.ac.una.attendancerecorderws.model.EmployeeDtoWs;
import cr.ac.una.attendancerecorderws.service.EmployeeService;
import cr.ac.una.attendancerecorderws.util.EmployeeResponseWrapper;
import cr.ac.una.attendancerecorderws.util.Response;
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
    public EmployeeResponseWrapper saveEmployee(@WebParam(name = "employee") EmployeeDtoWs employeeDto) {
        Employee employee = new Employee(employeeDto);
        Response response = employeeService.saveEmployee(employee);
        EmployeeResponseWrapper wrapper = new EmployeeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setEmployee(new EmployeeDtoWs((Employee) response.getResult("Employee")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updateEmployee")
    public EmployeeResponseWrapper updateEmployee(@WebParam(name = "employee") EmployeeDtoWs employeeDto) {
        Employee employee = new Employee(employeeDto);
        Response response = employeeService.updateEmployee(employee);
        EmployeeResponseWrapper wrapper = new EmployeeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setEmployee(new EmployeeDtoWs((Employee) response.getResult("Employee")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "deleteEmployee")
    public EmployeeResponseWrapper deleteEmployee(@WebParam(name = "id") Long id) {
        Response response = employeeService.deleteEmployee(id);
        return new EmployeeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
    }

    @WebMethod(operationName = "findEmployeeById")
    public EmployeeResponseWrapper findEmployeeById(@WebParam(name = "id") Long id) {
        Response response = employeeService.findEmployeeById(id);
        EmployeeResponseWrapper wrapper = new EmployeeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setEmployee(new EmployeeDtoWs((Employee) response.getResult("Employee")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllEmployees")
    public EmployeeResponseWrapper findAllEmployees() {
        Response response = employeeService.findAllEmployees();
        EmployeeResponseWrapper wrapper = new EmployeeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List<Employee> employees = (List<Employee>) response.getResult("Employees");
            if (employees != null) {
                wrapper.setEmployees(employees.stream().map(EmployeeDtoWs::new).collect(Collectors.toList()));
            }
        }
        return wrapper;
    }

    @WebMethod(operationName = "findEmployeeByFol")
    public EmployeeResponseWrapper findEmployeeByFol(@WebParam(name = "fol") String fol) {
        Response response = employeeService.findEmployeeByFol(fol);
        EmployeeResponseWrapper wrapper = new EmployeeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setEmployee(new EmployeeDtoWs((Employee) response.getResult("Employee")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "authenticate")
    public EmployeeResponseWrapper authenticate(@WebParam(name = "fol") String fol, @WebParam(name = "password") String password) {
        Response response = employeeService.authenticate(fol, password);
        EmployeeResponseWrapper wrapper = new EmployeeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setEmployee(new EmployeeDtoWs((Employee) response.getResult("Employee")));
        }
        return wrapper;
    }
}