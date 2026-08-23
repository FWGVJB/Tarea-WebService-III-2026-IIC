package cr.ac.una.attendancerecorderws.model;

import java.io.Serializable;
import java.util.Objects;

public class ShiftDtoWs implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private EmployeeDtoWs employee;
    private TimeRecordDtoWs entryRecord;
    private TimeRecordDtoWs exitRecord;
    private Long version;
    private Boolean modified;

    public ShiftDtoWs() {
        this.modified = false;
    }

    public ShiftDtoWs(Shift shift) {
        this();
        this.id = shift.getId();
        if (shift.getEmployee() != null) {
            this.employee = new EmployeeDtoWs(shift.getEmployee());
        }
        if (shift.getEntryRecord() != null) {
            this.entryRecord = new TimeRecordDtoWs(shift.getEntryRecord());
        }
        if (shift.getExitRecord() != null) {
            this.exitRecord = new TimeRecordDtoWs(shift.getExitRecord());
        }
        this.version = shift.getVersion();
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

    public TimeRecordDtoWs getEntryRecord() {
        return entryRecord;
    }

    public void setEntryRecord(TimeRecordDtoWs entryRecord) {
        this.entryRecord = entryRecord;
    }

    public TimeRecordDtoWs getExitRecord() {
        return exitRecord;
    }

    public void setExitRecord(TimeRecordDtoWs exitRecord) {
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
        final ShiftDtoWs other = (ShiftDtoWs) obj;
        return Objects.equals(this.id, other.id);
    }
}