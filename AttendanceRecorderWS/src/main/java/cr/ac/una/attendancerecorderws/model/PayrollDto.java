package cr.ac.una.attendancerecorderws.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PayrollDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer month;
    private Integer year;
    private Double totalPayment;
    private List<PayrollDetailDto> details;
    private Long version;
    private Boolean modified;

    public PayrollDto() {
        this.details = new ArrayList<>();
        this.modified = false;
    }

    public PayrollDto(Payroll payroll) {
        this();
        this.id = payroll.getId();
        this.month = payroll.getMonth();
        this.year = payroll.getYear();
        this.totalPayment = payroll.getTotalPayment();
        this.version = payroll.getVersion();
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

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public List<PayrollDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<PayrollDetailDto> details) {
        this.details = details;
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
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final PayrollDto other = (PayrollDto) obj;
        return Objects.equals(this.id, other.id);
    }
}