/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author USUARIO UNA PZ
 */
@Entity
@Table(name = "RELOJUNA_SHIFTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shift.findAll", query = "SELECT r FROM Shift r"),
    @NamedQuery(name = "Shift.findByShiftId", query = "SELECT r FROM Shift r WHERE r.shiftId = :shiftId"),
    @NamedQuery(name = "Shift.findByShiftVersion", query = "SELECT r FROM Shift r WHERE r.shiftVersion = :shiftVersion")})
public class Shift implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELOJUNA_SHIFTS_SEQ01")
    @SequenceGenerator(name = "RELOJUNA_SHIFTS_SEQ01", sequenceName = "RELOJUNA.RELOJUNA_SHIFTS_SEQ01", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "SHIFT_ID")
    private BigDecimal shiftId;
    @Version
    @Basic(optional = false)
    @NotNull
    @Column(name = "SHIFT_VERSION")
    private Long shiftVersion;
    @JoinColumn(name = "SHIFT_EMPLOYEE", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne(optional = false)
    private Employee shiftEmployee;
    @JoinColumn(name = "SHIFT_SHIFT_REPORT", referencedColumnName = "SHIFT_REPORT_ID")
    @ManyToOne
    private ShiftReport shiftShiftReport;
    @JoinColumn(name = "SHIFT_EXIT_TIME_RECORD", referencedColumnName = "TIME_RECORD_ID")
    @ManyToOne
    private TimeRecord exitRecord;
    @JoinColumn(name = "SHIFT_ENTRY_TIME_RECORD", referencedColumnName = "TIME_RECORD_ID")
    @ManyToOne(optional = false)
    private TimeRecord entryRecord;

    public Shift() {
    }

    public Shift(BigDecimal shiftId) {
        this.shiftId = shiftId;
    }

    public Shift(BigDecimal shiftId, Long shiftVersion) {
        this.shiftId = shiftId;
        this.shiftVersion = shiftVersion;
    }

    public BigDecimal getShiftId() {
        return shiftId;
    }

    public void setShiftId(BigDecimal shiftId) {
        this.shiftId = shiftId;
    }

    public Long getShiftVersion() {
        return shiftVersion;
    }

    public void setShiftVersion(Long shiftVersion) {
        this.shiftVersion = shiftVersion;
    }

    public Employee getShiftEmployee() {
        return shiftEmployee;
    }

    public void setShiftEmployee(Employee shiftEmployee) {
        this.shiftEmployee = shiftEmployee;
    }

    public ShiftReport getShiftShiftReport() {
        return shiftShiftReport;
    }

    public void setShiftShiftReport(ShiftReport shiftShiftReport) {
        this.shiftShiftReport = shiftShiftReport;
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
        int hash = 0;
        hash += (shiftId != null ? shiftId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shift)) {
            return false;
        }
        Shift other = (Shift) object;
        if ((this.shiftId == null && other.shiftId != null) || (this.shiftId != null && !this.shiftId.equals(other.shiftId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.attendancerecorderws.model.Shift[ shiftId=" + shiftId + " ]";
    }
    
}