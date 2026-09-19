package cr.ac.una.attendancerecorderws.ws;

import cr.ac.una.attendancerecorderws.service.JasperService;
import cr.ac.una.attendancerecorderws.util.JasperResponseWrapper;
import cr.ac.una.attendancerecorderws.util.Response;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.time.LocalDate;
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
    
    @WebMethod(operationName = "generateShiftReport")
    public JasperResponseWrapper generateShiftReport(
            @WebParam(name = "startDate") String startDate,
            @WebParam(name = "endDate") String endDate,
            @WebParam(name = "shiftIdList") List<Long> shiftIdList,
            @WebParam(name = "timeRecordsAmount") int timeRecordsAmount,
            @WebParam(name = "employeesAmount") int employeesAmount,
            @WebParam(name = "totalHours") double totalHours,
            @WebParam(name = "types") String types) {

        LocalDate parsedStart = startDate != null ? LocalDate.parse(startDate) : null;
        LocalDate parsedEnd = endDate != null ? LocalDate.parse(endDate) : null;

        Response response = reportService.generateShiftReport(parsedStart, parsedEnd, shiftIdList, timeRecordsAmount, employeesAmount, totalHours, types);
        JasperResponseWrapper wrapper = new JasperResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPdfReport((byte[]) response.getResult("PdfReport"));
        }
        return wrapper;
    }
    
    @WebMethod(operationName = "generatePayrollReport")
    public JasperResponseWrapper generatePayrollReport(@WebParam(name = "id") Long id) {
        Response response = reportService.generatePayrollReport(id);
        JasperResponseWrapper wrapper = new JasperResponseWrapper(response.getStatus(), response.getResponseCode(), response.getMessage());
        if (Boolean.TRUE.equals(response.getStatus())) {
            wrapper.setPdfReport((byte[]) response.getResult("PdfReport"));
        }
        return wrapper;
    }

}
