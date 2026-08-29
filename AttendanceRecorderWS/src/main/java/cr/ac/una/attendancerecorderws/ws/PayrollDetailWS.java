package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.PayrollDetail;
import cr.ac.una.attendancerecorderws.model.PayrollDetailDtoWs;
import cr.ac.una.attendancerecorderws.service.PayrollDetailService;
import cr.ac.una.attendancerecorderws.util.Response;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

@WebService(serviceName = "PayrollDetailWS")
public class PayrollDetailWS {

    @EJB
    private PayrollDetailService payrollDetailService;

    @WebMethod(operationName = "savePayrollDetail")
    public PayrollDetailResponseWrapper savePayrollDetail(@WebParam(name = "payrollDetail") PayrollDetailDtoWs payrollDetailDto) {
        PayrollDetail detail = new PayrollDetail(payrollDetailDto);
        Response response = payrollDetailService.savePayrollDetail(detail);
        PayrollDetailResponseWrapper wrapper = new PayrollDetailResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPayrollDetail(new PayrollDetailDtoWs((PayrollDetail) response.getResult("PayrollDetail")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updatePayrollDetail")
    public PayrollDetailResponseWrapper updatePayrollDetail(@WebParam(name = "payrollDetail") PayrollDetailDtoWs payrollDetailDto) {
        PayrollDetail detail = new PayrollDetail(payrollDetailDto);
        Response response = payrollDetailService.updatePayrollDetail(detail);
        PayrollDetailResponseWrapper wrapper = new PayrollDetailResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPayrollDetail(new PayrollDetailDtoWs((PayrollDetail) response.getResult("PayrollDetail")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "deletePayrollDetail")
    public PayrollDetailResponseWrapper deletePayrollDetail(@WebParam(name = "id") Long id) {
        Response response = payrollDetailService.deletePayrollDetail(id);
        return new PayrollDetailResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
    }

    @WebMethod(operationName = "findPayrollDetailById")
    public PayrollDetailResponseWrapper findPayrollDetailById(@WebParam(name = "id") Long id) {
        Response response = payrollDetailService.findPayrollDetailById(id);
        PayrollDetailResponseWrapper wrapper = new PayrollDetailResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPayrollDetail(new PayrollDetailDtoWs((PayrollDetail) response.getResult("PayrollDetail")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllPayrollDetails")
    public PayrollDetailResponseWrapper findAllPayrollDetails() {
        Response response = payrollDetailService.findAllPayrollDetails();
        PayrollDetailResponseWrapper wrapper = new PayrollDetailResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List<PayrollDetail> payrollDetails = (List<PayrollDetail>) response.getResult("PayrollDetails");
            if (payrollDetails != null) {
                wrapper.setPayrollDetails(payrollDetails.stream().map(PayrollDetailDtoWs::new).collect(Collectors.toList()));
            }
        }
        return wrapper;
    }
}