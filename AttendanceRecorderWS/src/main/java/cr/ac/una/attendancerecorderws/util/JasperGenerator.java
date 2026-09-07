package cr.ac.una.attendancerecorderws.util;

import cr.ac.una.attendancerecorderws.model.EmployeeDtoWs;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperGenerator {
    
    public static byte[] generateEmployeeInformationReport(List<EmployeeDtoWs> employeeDtosWs) throws JRException {        
        InputStream jasperStream = JasperGenerator.class.getResourceAsStream("/cr/ac/una/attendancerecorderws/jasper/relojUNA_Employee_Information_Report.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeDtosWs);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
    }
    
}
