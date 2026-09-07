package cr.ac.una.attendancerecorderws.ws;

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

@WebService(serviceName = "ShiftReportWS")
public class ShiftReportWS {

    @EJB
    private ShiftReportService shiftReportService;

    @WebMethod(operationName = "saveShiftReport")
    public ShiftReportResponseWrapper saveShiftReport(@WebParam(name = "shiftReport") ShiftReportDtoWs shiftReportDtoWs) {
        Response response = shiftReportService.saveShiftReport(shiftReportDtoWs);
        ShiftReportResponseWrapper wrapper = new ShiftReportResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setShiftReport((ShiftReportDtoWs) response.getResult("ShiftReport"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updateShiftReport")
    public ShiftReportResponseWrapper updateShiftReport(@WebParam(name = "shiftReport") ShiftReportDtoWs shiftReportDtoWs) {
        Response response = shiftReportService.updateShiftReport(shiftReportDtoWs);
        ShiftReportResponseWrapper wrapper = new ShiftReportResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setShiftReport((ShiftReportDtoWs) response.getResult("ShiftReport"));
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
            wrapper.setShiftReport((ShiftReportDtoWs) response.getResult("ShiftReport"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllShiftReports")
    public ShiftReportResponseWrapper findAllShiftReports() {
        Response response = shiftReportService.findAllShiftReports();
        ShiftReportResponseWrapper wrapper = new ShiftReportResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List shiftReportResultList = (List) response.getResult("ShiftReports");
            List<ShiftReportDtoWs> shiftReports = new ArrayList<>();
            if (shiftReportResultList != null) {
                for (Object obj : shiftReportResultList) {
                    shiftReports.add((ShiftReportDtoWs) obj);
                }
            }
            wrapper.setShiftReports(shiftReports);
        }
        return wrapper;
    }
}