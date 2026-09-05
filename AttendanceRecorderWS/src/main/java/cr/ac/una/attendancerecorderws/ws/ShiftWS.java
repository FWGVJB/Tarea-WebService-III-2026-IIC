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
import java.util.List;
import java.util.stream.Collectors;

@WebService(serviceName = "ShiftWS")
public class ShiftWS {

    @EJB
    private ShiftService shiftService;

    @WebMethod(operationName = "saveShift")
    public ShiftResponseWrapper saveShift(@WebParam(name = "shift") ShiftDtoWs shiftDto) {
        Shift shift = new Shift(shiftDto);
        Response response = shiftService.saveShift(shift);
        ShiftResponseWrapper wrapper = new ShiftResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setShift(new ShiftDtoWs((Shift) response.getResult("Shift")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updateShift")
    public ShiftResponseWrapper updateShift(@WebParam(name = "shift") ShiftDtoWs shiftDto) {
        Shift shift = new Shift(shiftDto);
        Response response = shiftService.updateShift(shift);
        ShiftResponseWrapper wrapper = new ShiftResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setShift(new ShiftDtoWs((Shift) response.getResult("Shift")));
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
            wrapper.setShift(new ShiftDtoWs((Shift) response.getResult("Shift")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllShifts")
    public ShiftResponseWrapper findAllShifts() {
        Response response = shiftService.findAllShifts();
        ShiftResponseWrapper wrapper = new ShiftResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List<Shift> shifts = (List<Shift>) response.getResult("Shifts");
            if (shifts != null) {
                wrapper.setShifts(shifts.stream().map(ShiftDtoWs::new).collect(Collectors.toList()));
            }
        }
        return wrapper;
    }
}