package cr.ac.una.attendancerecorderws.service;

import cr.ac.una.attendancerecorderws.model.Employee;
import cr.ac.una.attendancerecorderws.model.EmployeeDtoWs;
import cr.ac.una.attendancerecorderws.util.JasperGenerator;
import cr.ac.una.attendancerecorderws.util.Response;
import cr.ac.una.attendancerecorderws.util.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;

@Stateless
@LocalBean
public class JasperService {
    
    private static final Logger LOG = Logger.getLogger(JasperService.class.getName());

    @EJB
    private EmployeeService employeeService;
    
    public Response generateEmployeeInformationReport(List<Long> idList) {
        try {
            List<EmployeeDtoWs> employees = new ArrayList<>();
            for (Long id : idList) {
                Response employeeResponse = employeeService.findEmployeeById(id);
                if (Boolean.TRUE.equals(employeeResponse.getStatus())) {
                    Employee employee = (Employee) employeeResponse.getResult("Employee");
                    employees.add(new EmployeeDtoWs(employee));
                }
            }
            if (employees.isEmpty()) {
                return new Response(false, ResponseCode.NOT_FOUND_ERROR, "No se encontraron empleados con los IDs ingresados.", "generateEmployeeInformationReport no employees found");
            }
            byte[] pdfBytes = JasperGenerator.generateEmployeeInformationReport(employees);
            return new Response(true, ResponseCode.SUCCESS, "", "", "PdfReport", pdfBytes);
        } catch (JRException ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al generar el reporte de empleados.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "Ocurrió un error al generar el reporte de empleados.", "generateEmployeeInformationReport " + ex.getMessage());
        }
    }
    
}
