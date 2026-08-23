package cr.ac.una.attendancerecorderws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RELOJUNA_PAYROLLS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payroll.findAll", query = "SELECT p FROM Payroll p"),
    @NamedQuery(name = "Payroll.findById", query = "SELECT p FROM Payroll p WHERE p.id = :id")
})
public class Payroll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELOJUNA_PAYROLLS_SEQ01")
    @SequenceGenerator(name = "RELOJUNA_PAYROLLS_SEQ01", sequenceName = "RELOJUNA.RELOJUNA_PAYROLLS_SEQ01", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PAYROLL_ID")
    private Long id;

    @Basic(optional = false)
    @Column(name = "PAYROLL_MONTH")
    private Integer month;

    @Basic(optional = false)
    @Column(name = "PAYROLL_YEAR")
    private Integer year;

    @Basic(optional = false)
    @Column(name = "PAYROLL_TOTAL_PAYMENT")
    private Double totalPayment;

    @Version
    @Basic(optional = false)
    @Column(name = "PAYROLL_VERSION")
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "payroll")
    private List<PayrollDetail> details;

    public Payroll() {
    }

    public Payroll(Long id) {
        this.id = id;
    }

    public Payroll(PayrollDto dto) {
        this.id = dto.getId();
        update(dto);
    }

    public void update(PayrollDto dto) {
        this.month = dto.getMonth();
        this.year = dto.getYear();
        this.totalPayment = dto.getTotalPayment();
        this.version = dto.getVersion();
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @XmlTransient
    public List<PayrollDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PayrollDetail> details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Payroll)) {
            return false;
        }
        Payroll other = (Payroll) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.attendancerecorderws.model.Payroll[ payrollId=" + id + " ]";
    }
    
}