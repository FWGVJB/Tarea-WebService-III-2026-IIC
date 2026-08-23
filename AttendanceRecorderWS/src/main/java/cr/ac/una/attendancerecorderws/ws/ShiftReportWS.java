package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.Shift;
import cr.ac.una.attendancerecorderws.model.ShiftDtoWs;
import cr.ac.una.attendancerecorderws.model.ShiftReport;
import cr.ac.una.attendancerecorderws.model.ShiftReportDtoWs;
import cr.ac.una.attendancerecorderws.service.ShiftReportService;
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
    public ShiftReportDtoWs saveShiftReport(@WebParam(name = "shiftReport") ShiftReportDtoWs shiftReportDtoWs) {
        ShiftReport shiftReport = new ShiftReport(shiftReportDtoWs);
        mapShiftsToEntity(shiftReportDtoWs, shiftReport);
        
        ShiftReport savedShiftReport = shiftReportService.saveShiftReport(shiftReport);
        return toDtoWithShifts(savedShiftReport);
    }

    @WebMethod(operationName = "updateShiftReport")
    public ShiftReportDtoWs updateShiftReport(@WebParam(name = "shiftReport") ShiftReportDtoWs shiftReportDtoWs) {
        ShiftReport shiftReport = new ShiftReport(shiftReportDtoWs);
        mapShiftsToEntity(shiftReportDtoWs, shiftReport);
        
        ShiftReport updatedShiftReport = shiftReportService.updateShiftReport(shiftReport);
        return toDtoWithShifts(updatedShiftReport);
    }

    @WebMethod(operationName = "deleteShiftReport")
    public void deleteShiftReport(@WebParam(name = "id") Long id) {
        shiftReportService.deleteShiftReport(id);
    }

    @WebMethod(operationName = "findShiftReportById")
    public ShiftReportDtoWs findShiftReportById(@WebParam(name = "id") Long id) {
        ShiftReport shiftReport = shiftReportService.findShiftReportById(id);
        return shiftReport != null ? toDtoWithShifts(shiftReport) : null;
    }

    @WebMethod(operationName = "findAllShiftReports")
    public List<ShiftReportDtoWs> findAllShiftReports() {
        return shiftReportService.findAllShiftReports()
                .stream()
                .map(this::toDtoWithShifts)
                .collect(Collectors.toList());
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