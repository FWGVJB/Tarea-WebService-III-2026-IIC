package cr.ac.una.attendancerecorderws.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeRecordDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDateTime timestamp;
    private Boolean manuallyAdded;
    private Long version;
    private Boolean modified;

    public TimeRecordDto() {
        this.modified = false;
        this.manuallyAdded = false;
    }

    public TimeRecordDto(TimeRecord timeRecord) {
        this();
        this.id = timeRecord.getId();
        this.timestamp = timeRecord.getTimestamp();
        this.manuallyAdded = timeRecord.getManuallyAdded() != null && timeRecord.getManuallyAdded().equals("T");
        this.version = timeRecord.getVersion();
    }

    public TimeRecord toEntity() {
        TimeRecord timeRecord = new TimeRecord();
        timeRecord.setId(this.id);
        timeRecord.setTimestamp(this.timestamp);
        timeRecord.setManuallyAdded(Boolean.TRUE.equals(this.manuallyAdded) ? "T" : "F");
        timeRecord.setVersion(this.version);
        return timeRecord;
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

    public Boolean getManuallyAdded() {
        return manuallyAdded;
    }

    public void setManuallyAdded(Boolean manuallyAdded) {
        this.manuallyAdded = manuallyAdded;
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
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final TimeRecordDto other = (TimeRecordDto) obj;
        return Objects.equals(this.id, other.id);
    }
}