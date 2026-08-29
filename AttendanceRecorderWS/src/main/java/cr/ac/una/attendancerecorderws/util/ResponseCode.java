package cr.ac.una.attendancerecorderws.util;

public enum ResponseCode {
    SUCCESS(200),
    ACCESS_ERROR(403),
    PERMISSION_ERROR(401),
    NOT_FOUND_ERROR(404),
    CLIENT_ERROR(400),
    INTERNAL_ERROR(500);

    private Integer value;

    private ResponseCode(Integer value) {
        this.setValue(value);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}