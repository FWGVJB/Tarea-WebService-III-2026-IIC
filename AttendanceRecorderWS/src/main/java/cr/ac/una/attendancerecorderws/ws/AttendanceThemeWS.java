package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.model.AttendanceThemeDtoWs;
import cr.ac.una.attendancerecorderws.service.AttendanceThemeService;
import cr.ac.una.attendancerecorderws.util.AttendanceThemeResponseWrapper;
import cr.ac.una.attendancerecorderws.util.Response;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "AttendanceThemeWS")
public class AttendanceThemeWS {

    @EJB
    private AttendanceThemeService attendanceThemeService;

    @WebMethod(operationName = "saveAttendanceTheme")
    public AttendanceThemeResponseWrapper saveAttendanceTheme(@WebParam(name = "attendanceTheme") AttendanceThemeDtoWs attendanceThemeDto) {
        Response response = attendanceThemeService.saveAttendanceTheme(attendanceThemeDto);
        AttendanceThemeResponseWrapper wrapper = new AttendanceThemeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setAttendanceTheme((AttendanceThemeDtoWs) response.getResult("AttendanceTheme"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "updateAttendanceTheme")
    public AttendanceThemeResponseWrapper updateAttendanceTheme(@WebParam(name = "attendanceTheme") AttendanceThemeDtoWs attendanceThemeDto) {
        Response response = attendanceThemeService.updateAttendanceTheme(attendanceThemeDto);
        AttendanceThemeResponseWrapper wrapper = new AttendanceThemeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setAttendanceTheme((AttendanceThemeDtoWs) response.getResult("AttendanceTheme"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "deleteAttendanceTheme")
    public AttendanceThemeResponseWrapper deleteAttendanceTheme(@WebParam(name = "id") Long id) {
        Response response = attendanceThemeService.deleteAttendanceTheme(id);
        return new AttendanceThemeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
    }

    @WebMethod(operationName = "findAttendanceThemeById")
    public AttendanceThemeResponseWrapper findAttendanceThemeById(@WebParam(name = "id") Long id) {
        Response response = attendanceThemeService.findAttendanceThemeById(id);
        AttendanceThemeResponseWrapper wrapper = new AttendanceThemeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setAttendanceTheme((AttendanceThemeDtoWs) response.getResult("AttendanceTheme"));
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAllAttendanceThemes")
    public AttendanceThemeResponseWrapper findAllAttendanceThemes() {
        Response response = attendanceThemeService.findAllAttendanceThemes();
        AttendanceThemeResponseWrapper wrapper = new AttendanceThemeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List resultList = (List) response.getResult("AttendanceThemes");
            List<AttendanceThemeDtoWs> themes = new ArrayList<>();
            if (resultList != null) {
                for (Object obj : resultList) {
                    themes.add((AttendanceThemeDtoWs) obj);
                }
            }
            wrapper.setAttendanceThemes(themes);
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAttendanceThemesByDateRange")
    public AttendanceThemeResponseWrapper findAttendanceThemesByDateRange(
            @WebParam(name = "startDate") String startDate,
            @WebParam(name = "endDate") String endDate) {

        LocalDate parsedStart = (startDate != null && !startDate.isBlank()) ? LocalDate.parse(startDate) : null;
        LocalDate parsedEnd = (endDate != null && !endDate.isBlank()) ? LocalDate.parse(endDate) : null;

        Response response = attendanceThemeService.findAttendanceThemesByDateRange(parsedStart, parsedEnd);
        AttendanceThemeResponseWrapper wrapper = new AttendanceThemeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            List resultList = (List) response.getResult("AttendanceThemes");
            List<AttendanceThemeDtoWs> themes = new ArrayList<>();
            if (resultList != null) {
                for (Object obj : resultList) {
                    themes.add((AttendanceThemeDtoWs) obj);
                }
            }
            wrapper.setAttendanceThemes(themes);
        }
        return wrapper;
    }

    @WebMethod(operationName = "findAttendanceThemeByDate")
    public AttendanceThemeResponseWrapper findAttendanceThemeByDate(@WebParam(name = "date") String date) {
        LocalDate parsedDate = (date != null && !date.isBlank()) ? LocalDate.parse(date) : null;
        Response response = attendanceThemeService.findAttendanceThemeByDate(parsedDate);
        AttendanceThemeResponseWrapper wrapper = new AttendanceThemeResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setAttendanceTheme((AttendanceThemeDtoWs) response.getResult("AttendanceTheme"));
        }
        return wrapper;
    }
}