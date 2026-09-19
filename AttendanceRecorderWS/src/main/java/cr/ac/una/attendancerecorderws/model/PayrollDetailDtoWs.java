package cr.ac.una.attendancerecorderws.model;

import java.io.Serializable;
import java.util.Objects;

public class PayrollDetailDtoWs implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private EmployeeDtoWs employee;
    private Double hourlyWage;
    private Double ordinaryHours;
    private Double extraHours;
    private Double doubleHours;
    private Double monthlySalary;
    private Long version;
    private Boolean modified;

    public PayrollDetailDtoWs() {
        this.modified = false;
    }

    public PayrollDetailDtoWs(PayrollDetail detail) {
        this();
        this.id = detail.getId();
        if (detail.getEmployee() != null) {
            this.employee = new EmployeeDtoWs(detail.getEmployee());
        }
        this.hourlyWage = detail.getHourlyWage();
        this.ordinaryHours = detail.getOrdinaryHours();
        this.extraHours = detail.getExtraHours();
        this.doubleHours = detail.getDoubleHours();
        this.monthlySalary = detail.getMonthlySalary();
        this.version = detail.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDtoWs getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDtoWs employee) {
        this.employee = employee;
    }

    public Double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(Double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }
    
    public Double getOrdinaryHours() {
        return ordinaryHours;
    }

    public void setOrdinaryHours(Double ordinaryHours) {
        this.ordinaryHours = ordinaryHours;
    }
    
    public Double getExtraHours() {
        return extraHours;
    }

    public void setExtraHours(Double extraHours) {
        this.extraHours = extraHours;
    }
    
    public Double getDoubleHours() {
        return doubleHours;
    }

    public void setDoubleHours(Double doubleHours) {
        this.doubleHours = doubleHours;
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
    
    public Double getTotalHours() {
        double ordinary = ordinaryHours != null ? ordinaryHours : 0.0;
        double extra = extraHours != null ? extraHours : 0.0;
        double doubleH = doubleHours != null ? doubleHours : 0.0;
        return ordinary + extra + doubleH;
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
        final PayrollDetailDtoWs other = (PayrollDetailDtoWs) obj;
        return Objects.equals(this.id, other.id);
    }
}