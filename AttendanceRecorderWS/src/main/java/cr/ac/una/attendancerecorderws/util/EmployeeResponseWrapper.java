package cr.ac.una.attendancerecorderws.util;

import cr.ac.una.attendancerecorderws.model.EmployeeDtoWs;
import java.util.List;

public class EmployeeResponseWrapper extends GeneralResponseWrapper {

    private EmployeeDtoWs employee;
    private List<EmployeeDtoWs> employees;

    public EmployeeResponseWrapper() {
        super();
    }

    public EmployeeResponseWrapper(boolean status, ResponseCode responseCode, String message) {
        super(status, responseCode, message);
    }

    public EmployeeDtoWs getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDtoWs employee) {
        this.employee = employee;
    }

    public List<EmployeeDtoWs> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDtoWs> employees) {
        this.employees = employees;
    }
}