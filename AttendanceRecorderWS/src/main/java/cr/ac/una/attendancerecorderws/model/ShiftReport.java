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
@Table(name = "RELOJUNA_SHIFT_REPORTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShiftReport.findAll", query = "SELECT r FROM ShiftReport r"),
    @NamedQuery(name = "ShiftReport.findById", query = "SELECT r FROM ShiftReport r WHERE r.id = :id")
})
public class ShiftReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELOJUNA_SHIFT_REPORTS_SEQ01")
    @SequenceGenerator(name = "RELOJUNA_SHIFT_REPORTS_SEQ01", sequenceName = "RELOJUNA.RELOJUNA_SHIFT_REPORTS_SEQ01", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "SHIFT_REPORT_ID")
    private Long id;

    @Basic(optional = false)
    @Column(name = "SHIFT_REPORT_MONTH")
    private Integer month;

    @Basic(optional = false)
    @Column(name = "SHIFT_REPORT_YEAR")
    private Integer year;

    @Version
    @Basic(optional = false)
    @Column(name = "SHIFT_REPORT_VERSION")
    private Long version;

    @JoinColumn(name = "SHIFT_REPORT_EMPLOYEE", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne(optional = false)
    private Employee employee;

    @OneToMany(mappedBy = "shiftReport")
    private List<Shift> shifts;

    public ShiftReport() {
    }

    public ShiftReport(Long id) {
        this.id = id;
    }

    public ShiftReport(ShiftReportDto dto) {
        this.id = dto.getId();
        update(dto);
    }

    public void update(ShiftReportDto dto) {
        this.month = dto.getMonth();
        this.year = dto.getYear();
        if (dto.getEmployee() != null) {
            this.employee = new Employee(dto.getEmployee().getId());
        }
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

    @XmlTransient
    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ShiftReport)) {
            return false;
        }
        ShiftReport other = (ShiftReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.attendancerecorderws.model.ShiftReport[ shiftReportId=" + id + " ]";
    }
    
}