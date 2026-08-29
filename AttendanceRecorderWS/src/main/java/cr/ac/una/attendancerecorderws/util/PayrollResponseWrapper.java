package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.PayrollDtoWs;
import cr.ac.una.attendancerecorderws.util.ResponseCode;
import java.util.List;

public class PayrollResponseWrapper extends GeneralResponseWrapper {

    private PayrollDtoWs payroll;
    private List<PayrollDtoWs> payrolls;

    public PayrollResponseWrapper() {
        super();
    }

    public PayrollResponseWrapper(boolean status, ResponseCode responseCode, String message) {
        super(status, responseCode, message);
    }

    public PayrollDtoWs getPayroll() {
        return payroll;
    }

    public void setPayroll(PayrollDtoWs payroll) {
        this.payroll = payroll;
    }

    public List<PayrollDtoWs> getPayrolls() {
        return payrolls;
    }

    public void setPayrolls(List<PayrollDtoWs> payrolls) {
        this.payrolls = payrolls;
    }
}