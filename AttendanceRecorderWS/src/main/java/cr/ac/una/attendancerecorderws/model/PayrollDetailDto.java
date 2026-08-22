package cr.ac.una.attendancerecorderws.model;

import java.io.Serializable;
import java.util.Objects;

public class PayrollDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private EmployeeDto employee;
    private Double hourlyWage;
    private Double workedHours;
    private Double monthlySalary;
    private Long version;
    private Boolean modified;

    public PayrollDetailDto() {
        this.modified = false;
    }

    public PayrollDetailDto(PayrollDetail detail) {
        this();
        this.id = detail.getId();
        if (detail.getEmployee() != null) {
            this.employee = new EmployeeDto(detail.getEmployee());
        }
        this.hourlyWage = detail.getHourlyWage();
        this.workedHours = detail.getWorkedHours();
        this.monthlySalary = detail.getMonthlySalary();
        this.version = detail.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }

    public Double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(Double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public Double getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(Double workedHours) {
        this.workedHours = workedHours;
    }

    public Double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(Double monthlySalary) {
        this.monthlySalary = monthlySalary;
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
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final PayrollDetailDto other = (PayrollDetailDto) obj;
        return Objects.equals(this.id, other.id);
    }
}