package cr.ac.una.attendancerecorderws.util;

public class JasperResponseWrapper extends GeneralResponseWrapper {
    
    private byte[] pdfReport;
    
    public JasperResponseWrapper() {
        super();
    }

    public JasperResponseWrapper(boolean status, ResponseCode responseCode, String message) {
        super(status, responseCode, message);
    }

    public byte[] getPdfReport() {
        return pdfReport;
    }

    public void setPdfReport(byte[] pdfReport) {
        this.pdfReport = pdfReport;
    }
    
}
