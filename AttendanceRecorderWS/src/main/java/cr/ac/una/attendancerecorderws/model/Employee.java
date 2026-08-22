package cr.ac.una.attendancerecorderws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "RELOJUNA_EMPLOYEES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findById", query = "SELECT e FROM Employee e WHERE e.id = :id"),
    @NamedQuery(name = "Employee.findByFol", query = "SELECT e FROM Employee e WHERE e.fol = :fol")
})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELOJUNA_EMPLOYEES_SEQ01")
    @SequenceGenerator(name = "RELOJUNA_EMPLOYEES_SEQ01", sequenceName = "RELOJUNA.RELOJUNA_EMPLOYEES_SEQ01", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPLOYEE_ID")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "EMPLOYEE_ID_CARD")
    private String idCard;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "EMPLOYEE_FOL")
    private String fol;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMPLOYEE_NAME")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMPLOYEE_FIRST_SURNAME")
    private String firstSurname;

    @Size(max = 50)
    @Column(name = "EMPLOYEE_SECOND_SURNAME")
    private String secondSurname;

    @Size(max = 50)
    @Column(name = "EMPLOYEE_PASSWORD")
    private String password;

    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPLOYEE_HOURLY_WAGE")
    private Double hourlyWage;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "EMPLOYEE_ADMINISTRATOR")
    private String administrator;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "EMPLOYEE_ACTIVE")
    private String active;

    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPLOYEE_BIRTH_DATE")
    private LocalDate birthDate;

    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "EMPLOYEE_AVATAR")
    private Byte[] avatar;

    @Version
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPLOYEE_VERSION")
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shiftReportEmployee")
    private List<ShiftReport> shiftReportsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shiftEmployee")
    private List<Shift> shiftsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "payrollDetailEmployee")
    private List<PayrollDetail> payrollDetailsList;

    public Employee() {
    }

    public Employee(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getFol() {
        return fol;
    }

    public void setFol(String fol) {
        this.fol = fol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(Double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(Byte[] avatar) {
        this.avatar = avatar;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @XmlTransient
    public List<ShiftReport> getShiftReportsList() {
        return shiftReportsList;
    }

    public void setShiftReportsList(List<ShiftReport> shiftReportsList) {
        this.shiftReportsList = shiftReportsList;
    }

    @XmlTransient
    public List<Shift> getShiftsList() {
        return shiftsList;
    }

    public void setShiftsList(List<Shift> shiftsList) {
        this.shiftsList = shiftsList;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.attendancerecorderws.model.Employee[ id=" + id + " ]";
    }
}