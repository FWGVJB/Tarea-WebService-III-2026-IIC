package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.Payroll;
import cr.ac.una.attendancerecorderws.model.PayrollDtoWs;
import cr.ac.una.attendancerecorderws.model.PayrollDetail;
import cr.ac.una.attendancerecorderws.model.PayrollDetailDtoWs;
import cr.ac.una.attendancerecorderws.service.PayrollService;
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
    public PayrollDtoWs savePayroll(@WebParam(name = "payroll") PayrollDtoWs payrollDto) {
        Payroll payroll = new Payroll(payrollDto);
        mapDetailsToEntity(payrollDto, payroll);
        
        Payroll saved = payrollService.savePayroll(payroll);
        return toDtoWithDetails(saved);
    }

    @WebMethod(operationName = "updatePayroll")
    public PayrollDtoWs updatePayroll(@WebParam(name = "payroll") PayrollDtoWs payrollDto) {
        Payroll payroll = new Payroll(payrollDto);
        mapDetailsToEntity(payrollDto, payroll);
        
        Payroll updated = payrollService.updatePayroll(payroll);
        return toDtoWithDetails(updated);
    }

    @WebMethod(operationName = "deletePayroll")
    public void deletePayroll(@WebParam(name = "id") Long id) {
        payrollService.deletePayroll(id);
    }

    @WebMethod(operationName = "findPayrollById")
    public PayrollDtoWs findPayrollById(@WebParam(name = "id") Long id) {
        Payroll payroll = payrollService.findPayrollById(id);
        return payroll != null ? toDtoWithDetails(payroll) : null;
    }

    @WebMethod(operationName = "findAllPayrolls")
    public List<PayrollDtoWs> findAllPayrolls() {
        return payrollService.findAllPayrolls()
                .stream()
                .map(this::toDtoWithDetails)
                .collect(Collectors.toList());
    }


    // METODOS AUXILIARES PARA MANEJAR LAS LISTAS DE DETALLES
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