package cr.ac.una.attendancerecorderws.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String value) {
        return value != null ? LocalDate.parse(value) : null;
    }

    @Override
    public String marshal(LocalDate value) {
        return value != null ? value.toString() : null;
    }
}
