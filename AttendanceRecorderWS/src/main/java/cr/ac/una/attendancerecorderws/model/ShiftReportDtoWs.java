package cr.ac.una.attendancerecorderws.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShiftReportDtoWs implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer month;
    private Integer year;
    private EmployeeDtoWs employee;
    private List<ShiftDtoWs> shifts;
    private Long version;
    private Boolean modified;

    public ShiftReportDtoWs() {
        this.shifts = new ArrayList<>();
        this.modified = false;
    }

    public ShiftReportDtoWs(ShiftReport report) {
        this();
        this.id = report.getId();
        this.month = report.getMonth();
        this.year = report.getYear();
        
        if (report.getEmployee() != null) {
            this.employee = new EmployeeDtoWs(report.getEmployee());
        }
        this.version = report.getVersion();
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

    public EmployeeDtoWs getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDtoWs employee) {
        this.employee = employee;
    }

    public List<ShiftDtoWs> getShifts() {
        return shifts;
    }

    public void setShifts(List<ShiftDtoWs> shifts) {
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
        final ShiftReportDtoWs other = (ShiftReportDtoWs) obj;
        return Objects.equals(this.id, other.id);
    }
}