package cr.ac.una.attendancerecorderws.util;

import cr.ac.una.attendancerecorderws.model.ShiftDtoWs;
import java.util.List;

public class ShiftResponseWrapper extends GeneralResponseWrapper {

    private ShiftDtoWs shift;
    private List<ShiftDtoWs> shifts;

    public ShiftResponseWrapper() {
        super();
    }

    public ShiftResponseWrapper(boolean status, ResponseCode responseCode, String message) {
        super(status, responseCode, message);
    }

    public ShiftDtoWs getShift() {
        return shift;
    }

    public void setShift(ShiftDtoWs shift) {
        this.shift = shift;
    }

    public List<ShiftDtoWs> getShifts() {
        return shifts;
    }

    public void setShifts(List<ShiftDtoWs> shifts) {
        this.shifts = shifts;
    }
}