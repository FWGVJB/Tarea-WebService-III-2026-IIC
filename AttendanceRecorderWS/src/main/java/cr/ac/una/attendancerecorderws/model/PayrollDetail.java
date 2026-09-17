package cr.ac.una.attendancerecorderws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "RELOJUNA_PAYROLL_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayrollDetail.findAll", query = "SELECT r FROM PayrollDetail r"),
    @NamedQuery(name = "PayrollDetail.findById", query = "SELECT r FROM PayrollDetail r WHERE r.id = :id")
})
public class PayrollDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELOJUNA_PAYROLL_DETAILS_SEQ01")
    @SequenceGenerator(name = "RELOJUNA_PAYROLL_DETAILS_SEQ01", sequenceName = "RELOJUNA.RELOJUNA_PAYROLL_DETAILS_SEQ01", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PAYROLL_DETAIL_ID")
    private Long id;

    @Basic(optional = false)
    @Column(name = "PAYROLL_DETAIL_HOURLY_WAGE")
    private Double hourlyWage;

    @Basic(optional = false)
    @Column(name = "PAYROLL_DETAIL_ORDINARY_HOURS")
    private Double ordinaryHours;
    
    @Basic(optional = false)
    @Column(name = "PAYROLL_DETAIL_EXTRA_HOURS")
    private Double extraHours;
    
    @Basic(optional = false)
    @Column(name = "PAYROLL_DETAIL_DOUBLE_HOURS")
    private Double doubleHours;

    @Basic(optional = false)
    @Column(name = "PAYROLL_DETAIL_MONTHLY_SALARY")
    private Double monthlySalary;

    @Version
    @Basic(optional = false)
    @Column(name = "PAYROLL_DETAIL_VERSION")
    private Long version;

    @JoinColumn(name = "PAYROLL_DETAIL_EMPLOYEE", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne(optional = false)
    private Employee employee;

    @JoinColumn(name = "PAYROLL_DETAIL_PAYROLL", referencedColumnName = "PAYROLL_ID")
    @ManyToOne(optional = false)
    private Payroll payroll;

    public PayrollDetail() {
    }

    public PayrollDetail(Long id) {
        this.id = id;
    }

    public PayrollDetail(PayrollDetailDtoWs dto) {
        this.id = dto.getId();
        update(dto);
    }

    public void update(PayrollDetailDtoWs dto) {
        if (dto.getEmployee() != null) {
            this.employee = new Employee(dto.getEmployee().getId());
        }
        this.hourlyWage = dto.getHourlyWage();
        this.ordinaryHours = dto.getOrdinaryHours();
        this.extraHours = dto.getExtraHours();
        this.doubleHours = dto.getDoubleHours();
        this.monthlySalary = dto.getMonthlySalary();
        this.version = dto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Payroll getPayroll() {
        return payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PayrollDetail)) {
            return false;
        }
        PayrollDetail other = (PayrollDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.attendancerecorderws.model.PayrollDetail[ payrollDetailId=" + id + " ]";
    }

}
