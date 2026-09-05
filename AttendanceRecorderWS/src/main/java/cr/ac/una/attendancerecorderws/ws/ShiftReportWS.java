package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.Shift;
import cr.ac.una.attendancerecorderws.model.ShiftDtoWs;
import cr.ac.una.attendancerecorderws.model.ShiftReport;
import cr.ac.una.attendancerecorderws.model.ShiftReportDtoWs;
import cr.ac.una.attendancerecorderws.service.ShiftReportService;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.ShiftReportResponseWrapper;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebService(serviceName = "ShiftReportWS")
public class ShiftReportWS {

    @EJB
    private ShiftReportService shiftReportService;

    @WebMethod(operationName = "saveShiftReport")
    public ShiftReportResponseWrapper saveShiftReport(@WebParam(name = "shiftReport") ShiftReportDtoWs shiftReportDtoWs) {
        ShiftReport shiftReport = new ShiftReport(shiftReportDtoWs);
        mapShiftsToEntity(shiftReportDtoWs, shiftReport);

        Response response = shiftReportService.saveShiftReport(shiftReport);
        ShiftReportResponseWrapper wrapper = new ShiftReportResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setShiftReport(toDtoWithShifts((ShiftReport) response.getResult("ShiftReport")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updateShiftReport")
    public ShiftReportResponseWrapper updateShiftReport(@WebParam(name = "shiftReport") ShiftReportDtoWs shiftReportDtoWs) {
        ShiftReport shiftReport = new ShiftReport(shiftReportDtoWs);
        mapShiftsToEntity(shiftReportDtoWs, shiftReport);

        Response response = shiftReportService.updateShiftReport(shiftReport);
        ShiftReportResponseWrapper wrapper = new ShiftReportResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setShiftReport(toDtoWithShifts((ShiftReport) response.getResult("ShiftReport")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "deleteShiftReport")
    public ShiftReportResponseWrapper deleteShiftReport(@WebParam(name = "id") Long id) {
        Response response = shiftReportService.deleteShiftReport(id);
        return new ShiftReportResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
    }

    @WebMethod(operationName = "findShiftReportById")
    public ShiftReportResponseWrapper findShiftReportById(@WebParam(name = "id") Long id) {
        Response response = shiftReportService.findShiftReportById(id);
        ShiftReportResponseWrapper wrapper = new ShiftReportResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setShiftReport(toDtoWithShifts((ShiftReport) response.getResult("ShiftReport")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllShiftReports")
    public ShiftReportResponseWrapper findAllShiftReports() {
        Response response = shiftReportService.findAllShiftReports();
        ShiftReportResponseWrapper wrapper = new ShiftReportResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List<ShiftReport> shiftReports = (List<ShiftReport>) response.getResult("ShiftReports");
            if (shiftReports != null) {
                wrapper.setShiftReports(shiftReports.stream().map(this::toDtoWithShifts).collect(Collectors.toList()));
            }
        }
        return wrapper;
    }

    private void mapShiftsToEntity(ShiftReportDtoWs shiftReportDtoWs, ShiftReport shiftReport) {
        if (shiftReportDtoWs.getShifts() != null) {
            shiftReport.setShifts(new ArrayList<>());
            for (ShiftDtoWs shiftDtoWs : shiftReportDtoWs.getShifts()) {
                Shift shift = new Shift(shiftDtoWs);
                shift.setShiftReport(shiftReport);
                shiftReport.getShifts().add(shift);
            }
        }
    }

    private ShiftReportDtoWs toDtoWithShifts(ShiftReport shiftReport) {
        ShiftReportDtoWs shiftReportDtoWs = new ShiftReportDtoWs(shiftReport);
        if (shiftReport.getShifts() != null) {
            for (Shift shift : shiftReport.getShifts()) {
                shiftReportDtoWs.getShifts().add(new ShiftDtoWs(shift));
            }
        }
        return shiftReportDtoWs;
    }
}