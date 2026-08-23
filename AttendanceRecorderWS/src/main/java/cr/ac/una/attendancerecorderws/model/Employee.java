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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "EMPLOYEE_ID")
    private Long id;

    @Basic(optional = false)
    @Column(name = "EMPLOYEE_ID_CARD", length = 20)
    private String idCard;

    @Basic(optional = false)
    @Column(name = "EMPLOYEE_FOL", length = 20)
    private String fol;

    @Basic(optional = false)
    @Column(name = "EMPLOYEE_NAME", length = 50)
    private String name;

    @Basic(optional = false)
    @Column(name = "EMPLOYEE_FIRST_SURNAME", length = 50)
    private String firstSurname;

    @Column(name = "EMPLOYEE_SECOND_SURNAME", length = 50)
    private String secondSurname;

    @Column(name = "EMPLOYEE_PASSWORD", length = 50)
    private String password;

    @Basic(optional = false)
    @Column(name = "EMPLOYEE_HOURLY_WAGE")
    private Double hourlyWage;

    @Basic(optional = false)
    @Column(name = "EMPLOYEE_ADMINISTRATOR", length = 1)
    private String administrator;

    @Basic(optional = false)
    @Column(name = "EMPLOYEE_ACTIVE", length = 1)
    private String active;

    @Basic(optional = false)
    @Column(name = "EMPLOYEE_BIRTH_DATE")
    private LocalDate birthDate;

    @Basic(optional = false)
    @Lob
    @Column(name = "EMPLOYEE_AVATAR")
    private Byte[] avatar;

    @Version
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_VERSION")
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<ShiftReport> shiftReports;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Shift> shifts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<PayrollDetail> payrollDetails;

    public Employee() {
    }

    public Employee(Long id) {
        this.id = id;
    }

    public Employee(EmployeeDto dto) {
        this.id = dto.getId();
        update(dto);
    }

    public void update(EmployeeDto dto) {
        this.idCard = dto.getIdCard();
        this.fol = dto.getFol();
        this.name = dto.getName();
        this.firstSurname = dto.getFirstSurname();
        this.secondSurname = dto.getSecondSurname();
        this.password = dto.getPassword();
        this.hourlyWage = dto.getHourlyWage();
        this.active = (dto.getActive() != null && dto.getActive()) ? "T" : "F";
        this.administrator = (dto.getAdministrator() != null && dto.getAdministrator()) ? "T" : "F";
        this.birthDate = dto.getBirthDate();
        this.avatar = dto.getAvatar();
        this.version = dto.getVersion();
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
    public List<ShiftReport> getShiftReports() {
        return shiftReports;
    }

    public void setShiftReports(List<ShiftReport> shiftReports) {
        this.shiftReports = shiftReports;
    }

    @XmlTransient
    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    @XmlTransient
    public List<PayrollDetail> getPayrollDetails() {
        return payrollDetails;
    }

    public void setPayrollDetails(List<PayrollDetail> payrollDetails) {
        this.payrollDetails = payrollDetails;
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
        final Employee other = (Employee) obj;
        return Objects.equals(this.id, other.id);
    }
}