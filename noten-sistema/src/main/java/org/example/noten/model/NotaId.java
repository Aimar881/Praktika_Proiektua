package org.example.noten.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class NotaId implements Serializable {

    private String ikasleNan;
    private int zatiId;

    public NotaId() {}

    public NotaId(String ikasleNan, int zatiId) {
        this.ikasleNan = ikasleNan;
        this.zatiId = zatiId;
    }

    public String getIkasleNan() { return ikasleNan; }
    public void setIkasleNan(String ikasleNan) { this.ikasleNan = ikasleNan; }

    public int getZatiId() { return zatiId; }
    public void setZatiId(int zatiId) { this.zatiId = zatiId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotaId)) return false;
        NotaId that = (NotaId) o;
        return zatiId == that.zatiId && Objects.equals(ikasleNan, that.ikasleNan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ikasleNan, zatiId);
    }
}