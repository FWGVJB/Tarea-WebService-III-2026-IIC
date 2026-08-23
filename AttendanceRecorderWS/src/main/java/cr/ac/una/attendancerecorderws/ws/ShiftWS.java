package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.Shift;
import cr.ac.una.attendancerecorderws.model.ShiftDtoWs;
import cr.ac.una.attendancerecorderws.service.ShiftService;
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
    public ShiftDtoWs saveShift(@WebParam(name = "shift") ShiftDtoWs shiftDto) {
        Shift shift = new Shift(shiftDto);
        Shift saved = shiftService.saveShift(shift);
        return new ShiftDtoWs(saved);
    }

    @WebMethod(operationName = "updateShift")
    public ShiftDtoWs updateShift(@WebParam(name = "shift") ShiftDtoWs shiftDto) {
        Shift shift = new Shift(shiftDto);
        Shift updated = shiftService.updateShift(shift);
        return new ShiftDtoWs(updated);
    }

    @WebMethod(operationName = "deleteShift")
    public void deleteShift(@WebParam(name = "id") Long id) {
        shiftService.deleteShift(id);
    }

    @WebMethod(operationName = "findShiftById")
    public ShiftDtoWs findShiftById(@WebParam(name = "id") Long id) {
        Shift shift = shiftService.findShiftById(id);
        return shift != null ? new ShiftDtoWs(shift) : null;
    }

    @WebMethod(operationName = "findAllShifts")
    public List<ShiftDtoWs> findAllShifts() {
        return shiftService.findAllShifts()
                .stream()
                .map(ShiftDtoWs::new)
                .collect(Collectors.toList());
    }
}