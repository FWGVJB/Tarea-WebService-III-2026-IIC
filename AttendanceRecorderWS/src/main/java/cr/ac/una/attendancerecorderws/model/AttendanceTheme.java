package cr.ac.una.attendancerecorderws.model;

import cr.ac.una.attendancerecorderws.util.LocalDateAdapter;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "RELOJUNA_ATTENDANCE_THEMES")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "AttendanceTheme.findAll", query = "SELECT a FROM AttendanceTheme a"),
    @NamedQuery(name = "AttendanceTheme.findById", query = "SELECT a FROM AttendanceTheme a WHERE a.id = :id")
})
public class AttendanceTheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELOJUNA_ATTENDANCE_THEMES_SEQ01")
    @SequenceGenerator(name = "RELOJUNA_ATTENDANCE_THEMES_SEQ01", sequenceName = "RELOJUNA.RELOJUNA_ATTENDANCE_THEMES_SEQ01", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ATTENDANCE_THEME_ID")
    private Long id;

    @Basic(optional = false)
    @Column(name = "ATTENDANCE_THEME_START")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate start;

    @Basic(optional = false)
    @Column(name = "ATTENDANCE_THEME_END")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate end;

    @Basic(optional = false)
    @Lob
    @Column(name = "ATTENDANCE_THEME_BACKGROUND")
    private Byte[] background;

    @Basic(optional = false)
    @Column(name = "ATTENDANCE_THEME_CLOCK", length = 20)
    private String clock;

    @Basic(optional = false)
    @Column(name = "ATTENDANCE_THEME_MAIN_COLOR", length = 7)
    private String mainColor;

    @Basic(optional = false)
    @Column(name = "ATTENDANCE_THEME_SECONDARY_COLOR", length = 7)
    private String secondaryColor;

    @Version
    @Basic(optional = false)
    @Column(name = "ATTENDANCE_THEME_VERSION")
    private Long version;

    public AttendanceTheme() {
    }

    public AttendanceTheme(Long id) {
        this.id = id;
    }

    public AttendanceTheme(AttendanceThemeDtoWs dto) {
        this.id = dto.getId();
        update(dto);
    }

    public void update(AttendanceThemeDtoWs dto) {
        this.start = dto.getStart();
        this.end = dto.getEnd();
        this.background = dto.getBackground();
        this.clock = dto.getClock();
        this.mainColor = dto.getMainColor();
        this.secondaryColor = dto.getSecondaryColor();
        this.version = dto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Byte[] getBackground() {
        return background;
    }

    public void setBackground(Byte[] background) {
        this.background = background;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getMainColor() {
        return mainColor;
    }

    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AttendanceTheme)) {
            return false;
        }
        AttendanceTheme other = (AttendanceTheme) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}