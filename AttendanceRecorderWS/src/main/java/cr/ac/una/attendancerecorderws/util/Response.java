package cr.ac.una.attendancerecorderws.util;

import java.io.Serializable;
import java.util.HashMap;

public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean status;
    private ResponseCode responseCode;
    private String message;
    private String innerMessage;
    private HashMap<String, Object> result;

    public Response() {
        this.result = new HashMap<>();
    }

    public Response(Boolean status, ResponseCode responseCode, String message, String innerMessage) {
        this.status = status;
        this.responseCode = responseCode;
        this.message = message;
        this.innerMessage = innerMessage;
        this.result = new HashMap<>();
    }

    public Response(Boolean status, ResponseCode responseCode, String message, String innerMessage, String name, Object result) {
        this.status = status;
        this.responseCode = responseCode;
        this.message = message;
        this.innerMessage = innerMessage;
        this.result = new HashMap<>();
        this.result.put(name, result);
    }

    public Response(Boolean status, ResponseCode responseCode, String message, String innerMessage, Object result) {
        this.status = status;
        this.responseCode = responseCode;
        this.message = message;
        this.innerMessage = innerMessage;
        this.result = new HashMap<>();
        this.result.put("[Object]", result);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

    public String getInnerMessage() {
        return innerMessage;
    }

    public void setInnerMessage(String innerMessage) {
        this.innerMessage = innerMessage;
    }

    public Object getResult(String name) {
        return result.get(name);
    }

    public void setResult(String name, Object result) {
        this.result.put(name, result);
    }

    public Object getResult() {
        return result.get("[Object]");
    }

    public void setResult(Object result) {
        this.result.put("[Object]", result);
    }
}