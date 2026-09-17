package cr.ac.una.attendancerecorderws.util;

import cr.ac.una.attendancerecorderws.model.EmployeeDtoWs;
import cr.ac.una.attendancerecorderws.model.ShiftDtoWs;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public final class JasperGenerator {

    private static final Logger LOG = Logger.getLogger(JasperGenerator.class.getName());

    private static final String CHECK_ICON_PATH = "/cr/ac/una/attendancerecorderws/resource/checkIcon.png";
    private static final String WINDOW_LOGO_PATH = "/cr/ac/una/attendancerecorderws/resource/windowLogo.jpg";
    private static final String EMPLOYEE_REPORT_PATH = "/cr/ac/una/attendancerecorderws/jasper/relojUNA_Employee_Information_Report.jasper";
    private static final String SHIFT_REPORT_PATH = "/cr/ac/una/attendancerecorderws/jasper/relojUNA_Shifts_Report.jasper";

    private static volatile JasperGenerator instance;

    private final byte[] checkIconBytes;
    private final byte[] windowLogoBytes;
    private final JasperReport employeeReport;
    private final JasperReport shiftReport;

    private JasperGenerator() {
        this.checkIconBytes = readResourceBytes(CHECK_ICON_PATH);
        this.windowLogoBytes = readResourceBytes(WINDOW_LOGO_PATH);
        this.employeeReport = loadReport(EMPLOYEE_REPORT_PATH);
        this.shiftReport = loadReport(SHIFT_REPORT_PATH);
    }

    public static JasperGenerator getInstance() {
        JasperGenerator result = instance;
        if (result == null) {
            synchronized (JasperGenerator.class) {
                result = instance;
                if (result == null) {
                    instance = result = new JasperGenerator();
                }
            }
        }
        return result;
    }

    private static byte[] readResourceBytes(String path) {
        try (InputStream is = JasperGenerator.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalStateException("No se encontró el recurso: " + path);
            }
            return is.readAllBytes();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "No se pudo leer el recurso " + path, ex);
            throw new IllegalStateException("No se pudo leer el recurso " + path, ex);
        }
    }

    private static JasperReport loadReport(String path) {
        try (InputStream is = JasperGenerator.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalStateException("No se encontró el reporte compilado: " + path);
            }
            return (JasperReport) JRLoader.loadObject(is);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "No se pudo cargar el reporte " + path, ex);
            throw new IllegalStateException("No se pudo cargar el reporte " + path, ex);
        }
    }

    public static byte[] generateEmployeeInformationReport(List<EmployeeDtoWs> employeeDtosWs) throws JRException {
        return getInstance().doGenerateEmployeeInformationReport(employeeDtosWs);
    }

    public static byte[] generateShiftReport(List<ShiftDtoWs> shifts, LocalDate startDate, LocalDate endDate,
            int timeRecordsAmount, int employeesAmount, double totalHours) throws JRException {
        return getInstance().doGenerateShiftReport(shifts, startDate, endDate, timeRecordsAmount, employeesAmount, totalHours);
    }

    private byte[] doGenerateEmployeeInformationReport(List<EmployeeDtoWs> employeeDtosWs) throws JRException {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeDtosWs);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("WINDOW_LOGO", new ByteArrayInputStream(windowLogoBytes));
        parameters.put("CHECK_ICON", checkIconBytes);

        JasperPrint jasperPrint = JasperFillManager.fillReport(employeeReport, parameters, dataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
    }

    private byte[] doGenerateShiftReport(List<ShiftDtoWs> shifts, LocalDate startDate, LocalDate endDate,
            int timeRecordsAmount, int employeesAmount, double totalHours) throws JRException {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(shifts);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("WINDOW_LOGO", new ByteArrayInputStream(windowLogoBytes));
        parameters.put("START_DATE", startDate != null ? Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null);
        parameters.put("END_DATE", endDate != null ? Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null);
        parameters.put("TIME_RECORDS_AMOUNT", (double) timeRecordsAmount);
        parameters.put("EMPLOYEES_AMOUNT", (double) employeesAmount);
        parameters.put("WORKED_HOURS", totalHours);

        JasperPrint jasperPrint = JasperFillManager.fillReport(shiftReport, parameters, dataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
    }
}