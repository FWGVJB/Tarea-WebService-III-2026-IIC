package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.Employee;
import cr.ac.una.attendancerecorderws.model.EmployeeDto;
import cr.ac.una.attendancerecorderws.service.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

@WebService(serviceName = "EmployeeWS")
@Stateless
public class EmployeeWS {

    @EJB
    private EmployeeService employeeService;

    @WebMethod(operationName = "saveEmployee")
    public EmployeeDto saveEmployee(@WebParam(name = "employee") EmployeeDto employeeDto) {
        Employee employee = employeeDto.toEntity();
        Employee saved = employeeService.saveEmployee(employee);
        return new EmployeeDto(saved);
    }

    @WebMethod(operationName = "updateEmployee")
    public EmployeeDto updateEmployee(@WebParam(name = "employee") EmployeeDto employeeDto) {
        Employee employee = employeeDto.toEntity();
        Employee updated = employeeService.updateEmployee(employee);
        return new EmployeeDto(updated);
    }

    @WebMethod(operationName = "deleteEmployee")
    public void deleteEmployee(@WebParam(name = "id") Long id) {
        employeeService.deleteEmployee(id);
    }

    @WebMethod(operationName = "findEmployeeById")
    public EmployeeDto findEmployeeById(@WebParam(name = "id") Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        return employee != null ? new EmployeeDto(employee) : null;
    }

    @WebMethod(operationName = "findAllEmployees")
    public List<EmployeeDto> findAllEmployees() {
        return employeeService.findAllEmployees()
                .stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    @WebMethod(operationName = "findEmployeeByFol")
    public EmployeeDto findEmployeeByFol(@WebParam(name = "fol") String fol) {
        Employee employee = employeeService.findEmployeeByFol(fol);
        return employee != null ? new EmployeeDto(employee) : null;
    }
}