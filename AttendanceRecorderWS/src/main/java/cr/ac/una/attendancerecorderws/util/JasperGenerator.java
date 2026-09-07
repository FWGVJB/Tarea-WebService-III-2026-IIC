package cr.ac.una.attendancerecorderws.util;

import cr.ac.una.attendancerecorderws.model.EmployeeDtoWs;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperGenerator {
    
    private static final String CHECK_ICON_PATH = "/cr/ac/una/attendancerecorderws/resource/checkIcon.png", WINDOW_LOGO_PATH = "/cr/ac/una/attendancerecorderws/resource/windowLogo.jpg";
    
    public static byte[] generateEmployeeInformationReport(List<EmployeeDtoWs> employeeDtosWs) throws JRException {
        InputStream jasperStream = JasperGenerator.class.getResourceAsStream("/cr/ac/una/attendancerecorderws/jasper/relojUNA_Employee_Information_Report.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeDtosWs);
        
        Map<String, Object> parameters = new HashMap<>();
        InputStream checkIcon = JasperGenerator.class.getResourceAsStream(CHECK_ICON_PATH);
        InputStream windowLogo = JasperGenerator.class.getResourceAsStream(WINDOW_LOGO_PATH);
        parameters.put("CHECK_ICON", checkIcon);
        parameters.put("WINDOW_LOGO", windowLogo);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
    }
    
}
