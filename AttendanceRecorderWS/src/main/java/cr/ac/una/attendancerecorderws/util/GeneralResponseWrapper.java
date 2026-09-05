package cr.ac.una.attendancerecorderws.util;

public class GeneralResponseWrapper {
    
    private boolean status;
    private ResponseCode responseCode;
    private String message;

    public GeneralResponseWrapper() {
    }

    public GeneralResponseWrapper(boolean status, ResponseCode responseCode, String message) {
        this.status = status;
        this.responseCode = responseCode;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}