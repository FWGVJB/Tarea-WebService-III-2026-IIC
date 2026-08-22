package cr.ac.una.attendancerecorderws.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShiftReportDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer month;
    private Integer year;
    private EmployeeDto employee;
    private List<ShiftDto> shifts;
    private Long version;
    private Boolean modified;

    public ShiftReportDto() {
        this.shifts = new ArrayList<>();
        this.modified = false;
    }

    public ShiftReportDto(ShiftReport report) {
        this();
        if (report.getShiftReportId() != null) {
            this.id = report.getShiftReportId().longValue();
        }
        
        this.month = (int) report.getShiftReportMonth();
        this.year = (int) report.getShiftReportYear();
        
        if (report.getShiftReportEmployee() != null) {
            this.employee = new EmployeeDto(report.getShiftReportEmployee());
        }
        if (report.getShiftReportVersion() != null) {
            this.version = report.getShiftReportVersion().longValue();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }

    public List<ShiftDto> getShifts() {
        return shifts;
    }

    public void setShifts(List<ShiftDto> shifts) {
        this.shifts = shifts;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShiftReportDto other = (ShiftReportDto) obj;
        return Objects.equals(this.id, other.id);
    }
}