package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.TimeRecord;
import cr.ac.una.attendancerecorderws.model.TimeRecordDtoWs;
import cr.ac.una.attendancerecorderws.service.TimeRecordService;
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
    public TimeRecordDtoWs saveTimeRecord(@WebParam(name = "timeRecord") TimeRecordDtoWs timeRecordDtoWs) {
        TimeRecord timeRecord = new TimeRecord(timeRecordDtoWs);
        TimeRecord saved = timeRecordService.saveTimeRecord(timeRecord);
        return new TimeRecordDtoWs(saved);
    }

    @WebMethod(operationName = "updateTimeRecord")
    public TimeRecordDtoWs updateTimeRecord(@WebParam(name = "timeRecord") TimeRecordDtoWs timeRecordDtoWs) {
        TimeRecord timeRecord = new TimeRecord(timeRecordDtoWs);
        TimeRecord updated = timeRecordService.updateTimeRecord(timeRecord);
        return new TimeRecordDtoWs(updated);
    }

    @WebMethod(operationName = "deleteTimeRecord")
    public void deleteTimeRecord(@WebParam(name = "id") Long id) {
        timeRecordService.deleteTimeRecord(id);
    }

    @WebMethod(operationName = "findTimeRecordById")
    public TimeRecordDtoWs findTimeRecordById(@WebParam(name = "id") Long id) {
        TimeRecord timeRecord = timeRecordService.findTimeRecordById(id);
        return timeRecord != null ? new TimeRecordDtoWs(timeRecord) : null;
    }

    @WebMethod(operationName = "findAllTimeRecords")
    public List<TimeRecordDtoWs> findAllTimeRecords() {
        return timeRecordService.findAllTimeRecords()
                .stream()
                .map(TimeRecordDtoWs::new)
                .collect(Collectors.toList());
    }
}