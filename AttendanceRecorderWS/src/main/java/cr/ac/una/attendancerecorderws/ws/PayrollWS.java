package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.PayrollDtoWs;
import cr.ac.una.attendancerecorderws.service.PayrollService;
import cr.ac.una.attendancerecorderws.util.PayrollResponseWrapper;
import cr.ac.una.attendancerecorderws.util.Response;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "PayrollWS")
public class PayrollWS {

    @EJB
    private PayrollService payrollService;

    @WebMethod(operationName = "savePayroll")
    public PayrollResponseWrapper savePayroll(@WebParam(name = "payroll") PayrollDtoWs payrollDto) {
        Response response = payrollService.savePayroll(payrollDto);
        PayrollResponseWrapper wrapper = new PayrollResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPayroll((PayrollDtoWs) response.getResult("Payroll"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "generatePayroll")
    public PayrollResponseWrapper generatePayroll(@WebParam(name = "month") Integer month, @WebParam(name = "year") Integer year) {
        Response response = payrollService.generatePayroll(month, year);
        PayrollResponseWrapper wrapper = new PayrollResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPayroll((PayrollDtoWs) response.getResult("Payroll"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updatePayroll")
    public PayrollResponseWrapper updatePayroll(@WebParam(name = "payroll") PayrollDtoWs payrollDto) {
        Response response = payrollService.updatePayroll(payrollDto);
        PayrollResponseWrapper wrapper = new PayrollResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPayroll((PayrollDtoWs) response.getResult("Payroll"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "deletePayroll")
    public PayrollResponseWrapper deletePayroll(@WebParam(name = "id") Long id) {
        Response response = payrollService.deletePayroll(id);
        return new PayrollResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
    }

    @WebMethod(operationName = "findPayrollById")
    public PayrollResponseWrapper findPayrollById(@WebParam(name = "id") Long id) {
        Response response = payrollService.findPayrollById(id);
        PayrollResponseWrapper wrapper = new PayrollResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPayroll((PayrollDtoWs) response.getResult("Payroll"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllPayrolls")
    public PayrollResponseWrapper findAllPayrolls() {
        Response response = payrollService.findAllPayrolls();
        PayrollResponseWrapper wrapper = new PayrollResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List payrollResultList = (List) response.getResult("Payrolls");
            List<PayrollDtoWs> payrolls = new ArrayList<>();
            if (payrollResultList != null) {
                for (Object obj : payrollResultList) {
                    payrolls.add((PayrollDtoWs) obj);
                }
            }
            wrapper.setPayrolls(payrolls);
        }
        return wrapper;
    }
}