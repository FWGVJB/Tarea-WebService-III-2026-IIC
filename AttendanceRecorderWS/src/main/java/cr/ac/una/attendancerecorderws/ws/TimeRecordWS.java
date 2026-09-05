package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.TimeRecord;
import cr.ac.una.attendancerecorderws.model.TimeRecordDtoWs;
import cr.ac.una.attendancerecorderws.service.TimeRecordService;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.TimeRecordResponseWrapper;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

@WebService(serviceName = "TimeRecordWS")
public class TimeRecordWS {

    @EJB
    private TimeRecordService timeRecordService;

    @WebMethod(operationName = "saveTimeRecord")
    public TimeRecordResponseWrapper saveTimeRecord(@WebParam(name = "timeRecord") TimeRecordDtoWs timeRecordDtoWs) {
        TimeRecord timeRecord = new TimeRecord(timeRecordDtoWs);
        Response response = timeRecordService.saveTimeRecord(timeRecord);
        TimeRecordResponseWrapper wrapper = new TimeRecordResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setTimeRecord(new TimeRecordDtoWs((TimeRecord) response.getResult("TimeRecord")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updateTimeRecord")
    public TimeRecordResponseWrapper updateTimeRecord(@WebParam(name = "timeRecord") TimeRecordDtoWs timeRecordDtoWs) {
        TimeRecord timeRecord = new TimeRecord(timeRecordDtoWs);
        Response response = timeRecordService.updateTimeRecord(timeRecord);
        TimeRecordResponseWrapper wrapper = new TimeRecordResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setTimeRecord(new TimeRecordDtoWs((TimeRecord) response.getResult("TimeRecord")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "deleteTimeRecord")
    public TimeRecordResponseWrapper deleteTimeRecord(@WebParam(name = "id") Long id) {
        Response response = timeRecordService.deleteTimeRecord(id);
        return new TimeRecordResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
    }

    @WebMethod(operationName = "findTimeRecordById")
    public TimeRecordResponseWrapper findTimeRecordById(@WebParam(name = "id") Long id) {
        Response response = timeRecordService.findTimeRecordById(id);
        TimeRecordResponseWrapper wrapper = new TimeRecordResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setTimeRecord(new TimeRecordDtoWs((TimeRecord) response.getResult("TimeRecord")));
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllTimeRecords")
    public TimeRecordResponseWrapper findAllTimeRecords() {
        Response response = timeRecordService.findAllTimeRecords();
        TimeRecordResponseWrapper wrapper = new TimeRecordResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List<TimeRecord> timeRecords = (List<TimeRecord>) response.getResult("TimeRecords");
            if (timeRecords != null) {
                wrapper.setTimeRecords(timeRecords.stream().map(TimeRecordDtoWs::new).collect(Collectors.toList()));
            }
        }
        return wrapper;
    }
}