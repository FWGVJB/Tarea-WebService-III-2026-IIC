package cr.ac.una.attendancerecorderws.util;

import cr.ac.una.attendancerecorderws.model.PayrollDetailDtoWs;
import java.util.List;

public class PayrollDetailResponseWrapper extends GeneralResponseWrapper {

    private PayrollDetailDtoWs payrollDetail;
    private List<PayrollDetailDtoWs> payrollDetails;

    public PayrollDetailResponseWrapper() {
        super();
    }

    public PayrollDetailResponseWrapper(boolean status, ResponseCode responseCode, String message) {
        super(status, responseCode, message);
    }

    public PayrollDetailDtoWs getPayrollDetail() {
        return payrollDetail;
    }

    public void setPayrollDetail(PayrollDetailDtoWs payrollDetail) {
        this.payrollDetail = payrollDetail;
    }

    public List<PayrollDetailDtoWs> getPayrollDetails() {
        return payrollDetails;
    }

    public void setPayrollDetails(List<PayrollDetailDtoWs> payrollDetails) {
        this.payrollDetails = payrollDetails;
    }
}