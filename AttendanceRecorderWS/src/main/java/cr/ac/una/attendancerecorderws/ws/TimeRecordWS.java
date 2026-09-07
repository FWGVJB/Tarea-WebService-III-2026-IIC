package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.TimeRecordDtoWs;
import cr.ac.una.attendancerecorderws.service.TimeRecordService;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.TimeRecordResponseWrapper;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "TimeRecordWS")
public class TimeRecordWS {

    @EJB
    private TimeRecordService timeRecordService;

    @WebMethod(operationName = "saveTimeRecord")
    public TimeRecordResponseWrapper saveTimeRecord(@WebParam(name = "timeRecord") TimeRecordDtoWs timeRecordDtoWs) {
        Response response = timeRecordService.saveTimeRecord(timeRecordDtoWs);
        TimeRecordResponseWrapper wrapper = new TimeRecordResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setTimeRecord((TimeRecordDtoWs) response.getResult("TimeRecord"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updateTimeRecord")
    public TimeRecordResponseWrapper updateTimeRecord(@WebParam(name = "timeRecord") TimeRecordDtoWs timeRecordDtoWs) {
        Response response = timeRecordService.updateTimeRecord(timeRecordDtoWs);
        TimeRecordResponseWrapper wrapper = new TimeRecordResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setTimeRecord((TimeRecordDtoWs) response.getResult("TimeRecord"));
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
            wrapper.setTimeRecord((TimeRecordDtoWs) response.getResult("TimeRecord"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllTimeRecords")
    public TimeRecordResponseWrapper findAllTimeRecords() {
        Response response = timeRecordService.findAllTimeRecords();
        TimeRecordResponseWrapper wrapper = new TimeRecordResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List timeRecordResultList = (List) response.getResult("TimeRecords");
            List<TimeRecordDtoWs> timeRecords = new ArrayList<>();
            if (timeRecordResultList != null) {
                for (Object obj : timeRecordResultList) {
                    timeRecords.add((TimeRecordDtoWs) obj);
                }
            }
            wrapper.setTimeRecords(timeRecords);
        }
        return wrapper;
    }
}