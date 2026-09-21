package cr.ac.una.attendancerecorderws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Cacheable;
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
@Cacheable(false)
@Table(name = "RELOJUNA_SHIFTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shift.findAll", query = "SELECT s FROM Shift s"),
    @NamedQuery(name = "Shift.findById", query = "SELECT s FROM Shift s WHERE s.id = :id"),
    @NamedQuery(name = "Shift.findByEmployee", query = "SELECT s FROM Shift s WHERE s.employee = :employee"),
    @NamedQuery(name = "Shift.countByEmployee", query = "SELECT COUNT(s) FROM Shift s WHERE s.employee.id = :employeeId"),
    @NamedQuery(name = "Shift.search", query = "SELECT s FROM Shift s "
        + "LEFT JOIN s.entryRecord entryRecord "
        + "LEFT JOIN s.exitRecord exitRecord "
        + "WHERE (:employeeId IS NULL OR s.employee.id = :employeeId) "
        + "AND (:consistency = 'ALL' "
        + "OR (:consistency = 'CONSISTENT' AND entryRecord IS NOT NULL AND exitRecord IS NOT NULL) "
        + "OR (:consistency = 'INCONSISTENT' AND (entryRecord IS NULL OR exitRecord IS NULL))) "
        + "AND (:startDate IS NULL "
        + "OR (entryRecord IS NOT NULL AND entryRecord.timestamp >= :startDate) "
        + "OR (exitRecord IS NOT NULL AND exitRecord.timestamp >= :startDate)) "
        + "AND (:endDate IS NULL "
        + "OR (entryRecord IS NOT NULL AND entryRecord.timestamp <= :endDate) "
        + "OR (exitRecord IS NOT NULL AND exitRecord.timestamp <= :endDate))")
    })
public class Shift implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELOJUNA_SHIFTS_SEQ01")
    @SequenceGenerator(name = "RELOJUNA_SHIFTS_SEQ01", sequenceName = "RELOJUNA.RELOJUNA_SHIFTS_SEQ01", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "SHIFT_ID")
    private Long id;
    
    @Version
    @Basic(optional = false)
    @Column(name = "SHIFT_VERSION")
    private Long version;
    
    @JoinColumn(name = "SHIFT_EMPLOYEE", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne(optional = false)
    private Employee employee;
    
    @JoinColumn(name = "SHIFT_EXIT_TIME_RECORD", referencedColumnName = "TIME_RECORD_ID")
    @ManyToOne
    private TimeRecord exitRecord;
    
    @JoinColumn(name = "SHIFT_ENTRY_TIME_RECORD", referencedColumnName = "TIME_RECORD_ID")
    @ManyToOne
    private TimeRecord entryRecord;

    public Shift() {
    }

    public Shift(Long id) {
        this.id = id;
    }

    public Shift(ShiftDtoWs dto) {
        this.id = dto.getId();
        update(dto);
    }

    public void update(ShiftDtoWs dto) {
        if (dto.getEmployee() != null) {
            this.employee = new Employee(dto.getEmployee().getId());
        }
        if (dto.getEntryRecord() != null) {
            this.entryRecord = new TimeRecord(dto.getEntryRecord().getId());
        }
        else this.entryRecord = null;
        if (dto.getExitRecord() != null) {
            this.exitRecord = new TimeRecord(dto.getExitRecord().getId());
        }
        else this.exitRecord = null;
        this.version = dto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TimeRecord getExitRecord() {
        return exitRecord;
    }

    public void setExitRecord(TimeRecord exitRecord) {
        this.exitRecord = exitRecord;
    }

    public TimeRecord getEntryRecord() {
        return entryRecord;
    }

    public void setEntryRecord(TimeRecord entryRecord) {
        this.entryRecord = entryRecord;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Shift)) {
            return false;
        }
        Shift other = (Shift) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.attendancerecorderws.model.Shift[ id=" + id + " ]";
    }
}