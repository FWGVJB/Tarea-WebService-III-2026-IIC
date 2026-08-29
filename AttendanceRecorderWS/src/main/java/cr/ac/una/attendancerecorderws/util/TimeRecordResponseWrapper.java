package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.TimeRecordDtoWs;
import cr.ac.una.attendancerecorderws.util.ResponseCode;
import java.util.List;

public class TimeRecordResponseWrapper extends GeneralResponseWrapper {

    private TimeRecordDtoWs timeRecord;
    private List<TimeRecordDtoWs> timeRecords;

    public TimeRecordResponseWrapper() {
        super();
    }

    public TimeRecordResponseWrapper(boolean status, ResponseCode responseCode, String message) {
        super(status, responseCode, message);
    }

    public TimeRecordDtoWs getTimeRecord() {
        return timeRecord;
    }

    public void setTimeRecord(TimeRecordDtoWs timeRecord) {
        this.timeRecord = timeRecord;
    }

    public List<TimeRecordDtoWs> getTimeRecords() {
        return timeRecords;
    }

    public void setTimeRecords(List<TimeRecordDtoWs> timeRecords) {
        this.timeRecords = timeRecords;
    }
}