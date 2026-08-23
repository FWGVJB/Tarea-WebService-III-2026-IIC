package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.PayrollDetail;
import cr.ac.una.attendancerecorderws.model.PayrollDetailDtoWs;
import cr.ac.una.attendancerecorderws.service.PayrollDetailService;
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
    public PayrollDetailDtoWs savePayrollDetail(@WebParam(name = "payrollDetail") PayrollDetailDtoWs payrollDetailDto) {
        PayrollDetail detail = new PayrollDetail(payrollDetailDto);
        PayrollDetail saved = payrollDetailService.savePayrollDetail(detail);
        return new PayrollDetailDtoWs(saved);
    }

    @WebMethod(operationName = "updatePayrollDetail")
    public PayrollDetailDtoWs updatePayrollDetail(@WebParam(name = "payrollDetail") PayrollDetailDtoWs payrollDetailDto) {
        PayrollDetail detail = new PayrollDetail(payrollDetailDto);
        PayrollDetail updated = payrollDetailService.updatePayrollDetail(detail);
        return new PayrollDetailDtoWs(updated);
    }

    @WebMethod(operationName = "deletePayrollDetail")
    public void deletePayrollDetail(@WebParam(name = "id") Long id) {
        payrollDetailService.deletePayrollDetail(id);
    }

    @WebMethod(operationName = "findPayrollDetailById")
    public PayrollDetailDtoWs findPayrollDetailById(@WebParam(name = "id") Long id) {
        PayrollDetail detail = payrollDetailService.findPayrollDetailById(id);
        return detail != null ? new PayrollDetailDtoWs(detail) : null;
    }

    @WebMethod(operationName = "findAllPayrollDetails")
    public List<PayrollDetailDtoWs> findAllPayrollDetails() {
        return payrollDetailService.findAllPayrollDetails()
                .stream()
                .map(PayrollDetailDtoWs::new)
                .collect(Collectors.toList());
    }
}