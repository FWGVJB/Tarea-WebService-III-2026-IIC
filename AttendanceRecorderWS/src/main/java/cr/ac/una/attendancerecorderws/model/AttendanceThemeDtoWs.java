package cr.ac.una.attendancerecorderws.model;

import cr.ac.una.attendancerecorderws.util.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class AttendanceThemeDtoWs implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate start;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate end;

    private Byte[] background;
    private String clock;
    private String mainColor;
    private String secondaryColor;
    private Long version;
    private Boolean modified;

    public AttendanceThemeDtoWs() {
        this.modified = false;
    }

    public AttendanceThemeDtoWs(AttendanceTheme attendanceTheme) {
        this();
        this.id = attendanceTheme.getId();
        this.start = attendanceTheme.getStart();
        this.end = attendanceTheme.getEnd();
        this.background = attendanceTheme.getBackground();
        this.clock = attendanceTheme.getClock();
        this.mainColor = attendanceTheme.getMainColor();
        this.secondaryColor = attendanceTheme.getSecondaryColor();
        this.version = attendanceTheme.getVersion();
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

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
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
        final AttendanceThemeDtoWs other = (AttendanceThemeDtoWs) obj;
        return Objects.equals(this.id, other.id);
    }
    
}