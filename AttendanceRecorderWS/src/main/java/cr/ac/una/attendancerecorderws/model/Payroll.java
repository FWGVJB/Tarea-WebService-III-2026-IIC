/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.attendancerecorderws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "RELOJUNA_PAYROLLS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payroll.findAll", query = "SELECT r FROM Payroll r"),
    @NamedQuery(name = "Payroll.findByPayrollId", query = "SELECT r FROM Payroll r WHERE r.payrollId = :payrollId"),
    @NamedQuery(name = "Payroll.findByPayrollMonth", query = "SELECT r FROM Payroll r WHERE r.payrollMonth = :payrollMonth"),
    @NamedQuery(name = "Payroll.findByPayrollYear", query = "SELECT r FROM Payroll r WHERE r.payrollYear = :payrollYear"),
    @NamedQuery(name = "Payroll.findByPayrollTotalPayment", query = "SELECT r FROM Payroll r WHERE r.payrollTotalPayment = :payrollTotalPayment"),
    @NamedQuery(name = "Payroll.findByPayrollVersion", query = "SELECT r FROM Payroll r WHERE r.payrollVersion = :payrollVersion")})
public class Payroll implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_ID")
    private BigDecimal payrollId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_MONTH")
    private short payrollMonth;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_YEAR")
    private BigInteger payrollYear;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_TOTAL_PAYMENT")
    private BigDecimal payrollTotalPayment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_VERSION")
    private BigInteger payrollVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "payrollDetailPayroll")
    private List<PayrollDetail> payrollDetailsList;

    public Payroll() {
    }

    public Payroll(BigDecimal payrollId) {
        this.payrollId = payrollId;
    }

    public Payroll(BigDecimal payrollId, short payrollMonth, BigInteger payrollYear, BigDecimal payrollTotalPayment, BigInteger payrollVersion) {
        this.payrollId = payrollId;
        this.payrollMonth = payrollMonth;
        this.payrollYear = payrollYear;
        this.payrollTotalPayment = payrollTotalPayment;
        this.payrollVersion = payrollVersion;
    }

    public BigDecimal getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(BigDecimal payrollId) {
        this.payrollId = payrollId;
    }

    public short getPayrollMonth() {
        return payrollMonth;
    }

    public void setPayrollMonth(short payrollMonth) {
        this.payrollMonth = payrollMonth;
    }

    public BigInteger getPayrollYear() {
        return payrollYear;
    }

    public void setPayrollYear(BigInteger payrollYear) {
        this.payrollYear = payrollYear;
    }

    public BigDecimal getPayrollTotalPayment() {
        return payrollTotalPayment;
    }

    public void setPayrollTotalPayment(BigDecimal payrollTotalPayment) {
        this.payrollTotalPayment = payrollTotalPayment;
    }

    public BigInteger getPayrollVersion() {
        return payrollVersion;
    }

    public void setPayrollVersion(BigInteger payrollVersion) {
        this.payrollVersion = payrollVersion;
    }

    @XmlTransient
    public List<PayrollDetail> getPayrollDetailsList() {
        return payrollDetailsList;
    }

    public void setPayrollDetailsList(List<PayrollDetail> payrollDetailsList) {
        this.payrollDetailsList = payrollDetailsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payrollId != null ? payrollId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payroll)) {
            return false;
        }
        Payroll other = (Payroll) object;
        if ((this.payrollId == null && other.payrollId != null) || (this.payrollId != null && !this.payrollId.equals(other.payrollId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.attendancerecorderws.model.Payroll[ payrollId=" + payrollId + " ]";
    }
    
}