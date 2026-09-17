package cr.ac.una.attendancerecorderws.util;

import cr.ac.una.attendancerecorderws.model.AttendanceThemeDtoWs;
import java.util.List;

public class AttendanceThemeResponseWrapper extends GeneralResponseWrapper {

    private AttendanceThemeDtoWs attendanceTheme;
    private List<AttendanceThemeDtoWs> attendanceThemes;

    public AttendanceThemeResponseWrapper() {
        super();
    }

    public AttendanceThemeResponseWrapper(boolean status, ResponseCode responseCode, String message) {
        super(status, responseCode, message);
    }

    public AttendanceThemeDtoWs getAttendanceTheme() {
        return attendanceTheme;
    }

    public void setAttendanceTheme(AttendanceThemeDtoWs attendanceTheme) {
        this.attendanceTheme = attendanceTheme;
    }

    public List<AttendanceThemeDtoWs> getAttendanceThemes() {
        return attendanceThemes;
    }

    public void setAttendanceThemes(List<AttendanceThemeDtoWs> attendanceThemes) {
        this.attendanceThemes = attendanceThemes;
    }
    
}