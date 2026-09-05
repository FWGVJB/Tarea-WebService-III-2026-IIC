package cr.ac.una.attendancerecorderws.util;

import cr.ac.una.attendancerecorderws.model.ShiftReportDtoWs;
import java.util.List;

public class ShiftReportResponseWrapper extends GeneralResponseWrapper {

    private ShiftReportDtoWs shiftReport;
    private List<ShiftReportDtoWs> shiftReports;

    public ShiftReportResponseWrapper() {
        super();
    }

    public ShiftReportResponseWrapper(boolean status, ResponseCode responseCode, String message) {
        super(status, responseCode, message);
    }

    public ShiftReportDtoWs getShiftReport() {
        return shiftReport;
    }

    public void setShiftReport(ShiftReportDtoWs shiftReport) {
        this.shiftReport = shiftReport;
    }

    public List<ShiftReportDtoWs> getShiftReports() {
        return shiftReports;
    }

    public void setShiftReports(List<ShiftReportDtoWs> shiftReports) {
        this.shiftReports = shiftReports;
    }
}