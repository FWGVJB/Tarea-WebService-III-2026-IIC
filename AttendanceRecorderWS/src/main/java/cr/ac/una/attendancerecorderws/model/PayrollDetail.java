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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author USUARIO UNA PZ
 */
@Entity
@Table(name = "RELOJUNA_PAYROLL_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayrollDetail.findAll", query = "SELECT r FROM PayrollDetail r"),
    @NamedQuery(name = "PayrollDetail.findByPayrollDetailId", query = "SELECT r FROM PayrollDetail r WHERE r.payrollDetailId = :payrollDetailId"),
    @NamedQuery(name = "PayrollDetail.findByPayrollDetailHourlyWage", query = "SELECT r FROM PayrollDetail r WHERE r.payrollDetailHourlyWage = :payrollDetailHourlyWage"),
    @NamedQuery(name = "PayrollDetail.findByPayrollDetailWorkedHours", query = "SELECT r FROM PayrollDetail r WHERE r.payrollDetailWorkedHours = :payrollDetailWorkedHours"),
    @NamedQuery(name = "PayrollDetail.findByPayrollDetailMonthlySalary", query = "SELECT r FROM PayrollDetail r WHERE r.payrollDetailMonthlySalary = :payrollDetailMonthlySalary"),
    @NamedQuery(name = "PayrollDetail.findByPayrollDetailVersion", query = "SELECT r FROM PayrollDetail r WHERE r.payrollDetailVersion = :payrollDetailVersion")})
public class PayrollDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_DETAIL_ID")
    private BigDecimal payrollDetailId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_DETAIL_HOURLY_WAGE")
    private BigDecimal payrollDetailHourlyWage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_DETAIL_WORKED_HOURS")
    private BigDecimal payrollDetailWorkedHours;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_DETAIL_MONTHLY_SALARY")
    private BigDecimal payrollDetailMonthlySalary;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_DETAIL_VERSION")
    private BigInteger payrollDetailVersion;
    @JoinColumn(name = "PAYROLL_DETAIL_EMPLOYEE", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne(optional = false)
    private Employee payrollDetailEmployee;
    @JoinColumn(name = "PAYROLL_DETAIL_PAYROLL", referencedColumnName = "PAYROLL_ID")
    @ManyToOne(optional = false)
    private Payroll payrollDetailPayroll;

    public PayrollDetail() {
    }

    public PayrollDetail(BigDecimal payrollDetailId) {
        this.payrollDetailId = payrollDetailId;
    }

    public PayrollDetail(BigDecimal payrollDetailId, BigDecimal payrollDetailHourlyWage, BigDecimal payrollDetailWorkedHours, BigDecimal payrollDetailMonthlySalary, BigInteger payrollDetailVersion) {
        this.payrollDetailId = payrollDetailId;
        this.payrollDetailHourlyWage = payrollDetailHourlyWage;
        this.payrollDetailWorkedHours = payrollDetailWorkedHours;
        this.payrollDetailMonthlySalary = payrollDetailMonthlySalary;
        this.payrollDetailVersion = payrollDetailVersion;
    }

    public BigDecimal getPayrollDetailId() {
        return payrollDetailId;
    }

    public void setPayrollDetailId(BigDecimal payrollDetailId) {
        this.payrollDetailId = payrollDetailId;
    }

    public BigDecimal getPayrollDetailHourlyWage() {
        return payrollDetailHourlyWage;
    }

    public void setPayrollDetailHourlyWage(BigDecimal payrollDetailHourlyWage) {
        this.payrollDetailHourlyWage = payrollDetailHourlyWage;
    }

    public BigDecimal getPayrollDetailWorkedHours() {
        return payrollDetailWorkedHours;
    }

    public void setPayrollDetailWorkedHours(BigDecimal payrollDetailWorkedHours) {
        this.payrollDetailWorkedHours = payrollDetailWorkedHours;
    }

    public BigDecimal getPayrollDetailMonthlySalary() {
        return payrollDetailMonthlySalary;
    }

    public void setPayrollDetailMonthlySalary(BigDecimal payrollDetailMonthlySalary) {
        this.payrollDetailMonthlySalary = payrollDetailMonthlySalary;
    }

    public BigInteger getPayrollDetailVersion() {
        return payrollDetailVersion;
    }

    public void setPayrollDetailVersion(BigInteger payrollDetailVersion) {
        this.payrollDetailVersion = payrollDetailVersion;
    }

    public Employee getPayrollDetailEmployee() {
        return payrollDetailEmployee;
    }

    public void setPayrollDetailEmployee(Employee payrollDetailEmployee) {
        this.payrollDetailEmployee = payrollDetailEmployee;
    }

    public Payroll getPayrollDetailPayroll() {
        return payrollDetailPayroll;
    }

    public void setPayrollDetailPayroll(Payroll payrollDetailPayroll) {
        this.payrollDetailPayroll = payrollDetailPayroll;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payrollDetailId != null ? payrollDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayrollDetail)) {
            return false;
        }
        PayrollDetail other = (PayrollDetail) object;
        if ((this.payrollDetailId == null && other.payrollDetailId != null) || (this.payrollDetailId != null && !this.payrollDetailId.equals(other.payrollDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.attendancerecorderws.model.PayrollDetail[ payrollDetailId=" + payrollDetailId + " ]";
    }
    
}