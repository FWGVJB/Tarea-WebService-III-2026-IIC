/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.attendancerecorderws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author USUARIO UNA PZ
 */
@Entity
@Table(name = "RELOJUNA_SHIFT_REPORTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShiftReport.findAll", query = "SELECT r FROM ShiftReport r"),
    @NamedQuery(name = "ShiftReport.findByShiftReportId", query = "SELECT r FROM ShiftReport r WHERE r.shiftReportId = :shiftReportId"),
    @NamedQuery(name = "ShiftReport.findByShiftReportMonth", query = "SELECT r FROM ShiftReport r WHERE r.shiftReportMonth = :shiftReportMonth"),
    @NamedQuery(name = "ShiftReport.findByShiftReportYear", query = "SELECT r FROM ShiftReport r WHERE r.shiftReportYear = :shiftReportYear"),
    @NamedQuery(name = "ShiftReport.findByShiftReportVersion", query = "SELECT r FROM ShiftReport r WHERE r.shiftReportVersion = :shiftReportVersion")})
public class ShiftReport implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SHIFT_REPORT_ID")
    private BigDecimal shiftReportId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SHIFT_REPORT_MONTH")
    private short shiftReportMonth;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SHIFT_REPORT_YEAR")
    private short shiftReportYear;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SHIFT_REPORT_VERSION")
    private BigInteger shiftReportVersion;
    @JoinColumn(name = "SHIFT_REPORT_EMPLOYEE", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne(optional = false)
    private Employee shiftReportEmployee;
    @OneToMany(mappedBy = "shiftShiftReport")
    private List<Shift> shiftsList;

    public ShiftReport() {
    }

    public ShiftReport(BigDecimal shiftReportId) {
        this.shiftReportId = shiftReportId;
    }

    public ShiftReport(BigDecimal shiftReportId, short shiftReportMonth, short shiftReportYear, BigInteger shiftReportVersion) {
        this.shiftReportId = shiftReportId;
        this.shiftReportMonth = shiftReportMonth;
        this.shiftReportYear = shiftReportYear;
        this.shiftReportVersion = shiftReportVersion;
    }

    public BigDecimal getShiftReportId() {
        return shiftReportId;
    }

    public void setShiftReportId(BigDecimal shiftReportId) {
        this.shiftReportId = shiftReportId;
    }

    public short getShiftReportMonth() {
        return shiftReportMonth;
    }

    public void setShiftReportMonth(short shiftReportMonth) {
        this.shiftReportMonth = shiftReportMonth;
    }

    public short getShiftReportYear() {
        return shiftReportYear;
    }

    public void setShiftReportYear(short shiftReportYear) {
        this.shiftReportYear = shiftReportYear;
    }

    public BigInteger getShiftReportVersion() {
        return shiftReportVersion;
    }

    public void setShiftReportVersion(BigInteger shiftReportVersion) {
        this.shiftReportVersion = shiftReportVersion;
    }

    public Employee getShiftReportEmployee() {
        return shiftReportEmployee;
    }

    public void setShiftReportEmployee(Employee shiftReportEmployee) {
        this.shiftReportEmployee = shiftReportEmployee;
    }

    @XmlTransient
    public List<Shift> getShiftsList() {
        return shiftsList;
    }

    public void setShiftsList(List<Shift> shiftsList) {
        this.shiftsList = shiftsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shiftReportId != null ? shiftReportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShiftReport)) {
            return false;
        }
        ShiftReport other = (ShiftReport) object;
        if ((this.shiftReportId == null && other.shiftReportId != null) || (this.shiftReportId != null && !this.shiftReportId.equals(other.shiftReportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.attendancerecorderws.model.ShiftReport[ shiftReportId=" + shiftReportId + " ]";
    }
    
}