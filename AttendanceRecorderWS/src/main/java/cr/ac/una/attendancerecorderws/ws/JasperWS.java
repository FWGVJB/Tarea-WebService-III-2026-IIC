package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.service.JasperService;
import cr.ac.una.attendancerecorderws.util.JasperResponseWrapper;
import cr.ac.una.attendancerecorderws.util.Response;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

@WebService(serviceName = "JasperWS")
public class JasperWS {
    
    @EJB
    private JasperService reportService;

    @WebMethod(operationName = "generateEmployeeInformationReport")
    public JasperResponseWrapper generateEmployeeInformationReport(@WebParam(name = "idList") List<Long> idList) {
        Response response = reportService.generateEmployeeInformationReport(idList);
        JasperResponseWrapper wrapper = new JasperResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPdfReport((byte[]) response.getResult("PdfReport"));
        }
        return wrapper;
    }
    
}
