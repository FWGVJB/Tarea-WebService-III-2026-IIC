package cr.ac.una.attendancerecorderws.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class EmployeeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String idCard;
    private String fol;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String password;
    private Double hourlyWage;
    private Boolean administrator;
    private Boolean active;
    private LocalDate birthDate;
    private Byte[] avatar;
    private Long version;
    private Boolean modified;

    public EmployeeDto() {
        this.modified = false;
        this.active = false;
        this.administrator = false;
    }

    public EmployeeDto(Employee employee) {
        this();
        this.id = employee.getId();
        this.idCard = employee.getIdCard();
        this.fol = employee.getFol();
        this.name = employee.getName();
        this.firstSurname = employee.getFirstSurname();
        this.secondSurname = employee.getSecondSurname();
        this.password = employee.getPassword();
        this.hourlyWage = employee.getHourlyWage();
        this.active = employee.getActive() != null && employee.getActive().equals("T");
        this.administrator = employee.getAdministrator() != null && employee.getAdministrator().equals("T");
        this.birthDate = employee.getBirthDate();
        this.avatar = employee.getAvatar();
        this.version = employee.getVersion();
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

    public Boolean getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
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

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final EmployeeDto other = (EmployeeDto) obj;
        return Objects.equals(this.id, other.id);
    }
}