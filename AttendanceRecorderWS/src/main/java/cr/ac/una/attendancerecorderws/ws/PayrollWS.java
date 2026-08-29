package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.Payroll;
import cr.ac.una.attendancerecorderws.model.PayrollDtoWs;
import cr.ac.una.attendancerecorderws.model.PayrollDetail;
import cr.ac.una.attendancerecorderws.model.PayrollDetailDtoWs;
import cr.ac.una.attendancerecorderws.service.PayrollService;
import cr.ac.una.attendancerecorderws.util.Response;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebService(serviceName = "PayrollWS")
public class PayrollWS {

    @EJB
    private PayrollService payrollService;

    @WebMethod(operationName = "savePayroll")
    public PayrollResponseWrapper savePayroll(@WebParam(name = "payroll") PayrollDtoWs payrollDto) {
        Payroll payroll = new Payroll(payrollDto);
        mapDetailsToEntity(payrollDto, payroll);

        Response response = payrollService.savePayroll(payroll);
        PayrollResponseWrapper wrapper = new PayrollResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPayroll(toDtoWithDetails((Payroll) response.getResult("Payroll")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updatePayroll")
    public PayrollResponseWrapper updatePayroll(@WebParam(name = "payroll") PayrollDtoWs payrollDto) {
        Payroll payroll = new Payroll(payrollDto);
        mapDetailsToEntity(payrollDto, payroll);

        Response response = payrollService.updatePayroll(payroll);
        PayrollResponseWrapper wrapper = new PayrollResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPayroll(toDtoWithDetails((Payroll) response.getResult("Payroll")));
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
            wrapper.setPayroll(toDtoWithDetails((Payroll) response.getResult("Payroll")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllPayrolls")
    public PayrollResponseWrapper findAllPayrolls() {
        Response response = payrollService.findAllPayrolls();
        PayrollResponseWrapper wrapper = new PayrollResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List<Payroll> payrolls = (List<Payroll>) response.getResult("Payrolls");
            if (payrolls != null) {
                wrapper.setPayrolls(payrolls.stream().map(this::toDtoWithDetails).collect(Collectors.toList()));
            }
        }
        return wrapper;
    }

    private void mapDetailsToEntity(PayrollDtoWs dto, Payroll entity) {
        if (dto.getDetails() != null) {
            entity.setDetails(new ArrayList<>());
            for (PayrollDetailDtoWs detailDto : dto.getDetails()) {
                PayrollDetail detail = new PayrollDetail(detailDto);
                detail.setPayroll(entity);
                entity.getDetails().add(detail);
            }
        }
    }

    private PayrollDtoWs toDtoWithDetails(Payroll entity) {
        PayrollDtoWs dto = new PayrollDtoWs(entity);
        if (entity.getDetails() != null) {
            for (PayrollDetail detail : entity.getDetails()) {
                dto.getDetails().add(new PayrollDetailDtoWs(detail));
            }
        }
        return dto;
    }
}