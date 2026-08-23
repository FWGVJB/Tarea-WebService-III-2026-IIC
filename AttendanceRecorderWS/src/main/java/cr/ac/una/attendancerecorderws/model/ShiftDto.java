package cr.ac.una.attendancerecorderws.model;

import java.io.Serializable;
import java.util.Objects;

public class ShiftDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private EmployeeDto employee;
    private TimeRecordDto entryRecord;
    private TimeRecordDto exitRecord;
    private Long version;
    private Boolean modified;

    public ShiftDto() {
        this.modified = false;
    }

    public ShiftDto(Shift shift) {
        this();
        this.id = shift.getId();
        if (shift.getEmployee() != null) {
            this.employee = new EmployeeDto(shift.getEmployee());
        }
        if (shift.getEntryRecord() != null) {
            this.entryRecord = new TimeRecordDto(shift.getEntryRecord());
        }
        if (shift.getExitRecord() != null) {
            this.exitRecord = new TimeRecordDto(shift.getExitRecord());
        }
        this.version = shift.getVersion();
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

    public TimeRecordDto getEntryRecord() {
        return entryRecord;
    }

    public void setEntryRecord(TimeRecordDto entryRecord) {
        this.entryRecord = entryRecord;
    }

    public TimeRecordDto getExitRecord() {
        return exitRecord;
    }

    public void setExitRecord(TimeRecordDto exitRecord) {
        this.exitRecord = exitRecord;
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
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final ShiftDto other = (ShiftDto) obj;
        return Objects.equals(this.id, other.id);
    }
}