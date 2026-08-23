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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RELOJUNA_TIME_RECORDS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TimeRecord.findAll", query = "SELECT t FROM TimeRecord t"),
    @NamedQuery(name = "TimeRecord.findById", query = "SELECT t FROM TimeRecord t WHERE t.id = :id")
})
public class TimeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELOJUNA_TIME_RECORDS_SEQ01")
    @SequenceGenerator(name = "RELOJUNA_TIME_RECORDS_SEQ01", sequenceName = "RELOJUNA.RELOJUNA_TIME_RECORDS_SEQ01", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "TIME_RECORD_ID")
    private Long id;

    @Basic(optional = false)
    @Column(name = "TIME_RECORD_TIME_STAMP")
    private LocalDateTime timestamp;

    @Basic(optional = false)
    @Column(name = "TIME_RECORD_MANUALLY_ADDED", length = 1)
    private String manuallyAdded;

    @Version
    @Basic(optional = false)
    @Column(name = "TIME_RECORD_VERSION")
    private Long version;

    @OneToMany(mappedBy = "exitRecord")
    private List<Shift> exitShifts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entryRecord")
    private List<Shift> entryShifts;

    public TimeRecord() {
    }

    public TimeRecord(Long id) {
        this.id = id;
    }

    public TimeRecord(TimeRecordDto dto) {
        this.id = dto.getId();
        update(dto);
    }

    public void update(TimeRecordDto dto) {
        this.timestamp = dto.getTimestamp();
        this.manuallyAdded = (dto.getManuallyAdded() != null && dto.getManuallyAdded()) ? "T" : "F";
        this.version = dto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getManuallyAdded() {
        return manuallyAdded;
    }

    public void setManuallyAdded(String manuallyAdded) {
        this.manuallyAdded = manuallyAdded;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @XmlTransient
    public List<Shift> getExitShifts() {
        return exitShifts;
    }

    public void setExitShifts(List<Shift> exitShifts) {
        this.exitShifts = exitShifts;
    }

    @XmlTransient
    public List<Shift> getEntryShifts() {
        return entryShifts;
    }

    public void setEntryShifts(List<Shift> entryShifts) {
        this.entryShifts = entryShifts;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TimeRecord)) {
            return false;
        }
        TimeRecord other = (TimeRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}