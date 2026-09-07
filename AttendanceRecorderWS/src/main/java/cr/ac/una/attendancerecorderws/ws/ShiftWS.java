package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.Shift;
import cr.ac.una.attendancerecorderws.model.ShiftDtoWs;
import cr.ac.una.attendancerecorderws.service.ShiftService;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.ShiftResponseWrapper;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebService(serviceName = "ShiftWS")
public class ShiftWS {

    @EJB
    private ShiftService shiftService;

    @WebMethod(operationName = "saveShift")
    public ShiftResponseWrapper saveShift(@WebParam(name = "shift") ShiftDtoWs shiftDto) {
        Response response = shiftService.saveShift(shiftDto);
        ShiftResponseWrapper wrapper = new ShiftResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setShift((ShiftDtoWs) response.getResult("Shift"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updateShift")
    public ShiftResponseWrapper updateShift(@WebParam(name = "shift") ShiftDtoWs shiftDto) {
        Response response = shiftService.updateShift(shiftDto);
        ShiftResponseWrapper wrapper = new ShiftResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setShift((ShiftDtoWs) response.getResult("Shift"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "deleteShift")
    public ShiftResponseWrapper deleteShift(@WebParam(name = "id") Long id) {
        Response response = shiftService.deleteShift(id);
        return new ShiftResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
    }

    @WebMethod(operationName = "findShiftById")
    public ShiftResponseWrapper findShiftById(@WebParam(name = "id") Long id) {
        Response response = shiftService.findShiftById(id);
        ShiftResponseWrapper wrapper = new ShiftResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setShift((ShiftDtoWs) response.getResult("Shift"));
        }
        return wrapper;
    }
    
    @WebMethod(operationName = "findShiftsByEmployee")
    public ShiftResponseWrapper findShiftsByEmployee(@WebParam(name = "employeeId") Long employeeId) {
        Response response = shiftService.findShiftsByEmployee(employeeId);
        ShiftResponseWrapper wrapper = new ShiftResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List<Shift> shifts = (List<Shift>) response.getResult("Shifts");
            if (shifts != null) {
                wrapper.setShifts(shifts.stream().map(ShiftDtoWs::new).collect(Collectors.toList()));
            }
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllShifts")
    public ShiftResponseWrapper findAllShifts() {
        Response response = shiftService.findAllShifts();
        ShiftResponseWrapper wrapper = new ShiftResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List shiftResultList = (List) response.getResult("Shifts");
            List<ShiftDtoWs> shifts = new ArrayList<>();
            if (shiftResultList != null) {
                for (Object obj : shiftResultList) {
                    shifts.add((ShiftDtoWs) obj);
                }
            }
            wrapper.setShifts(shifts);
        }
        return wrapper;
    }
}