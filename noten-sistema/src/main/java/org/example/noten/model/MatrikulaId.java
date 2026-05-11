package org.example.noten.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MatrikulaId implements Serializable {

    private String ikasleNan;
    private int ikasgaiId;

    public MatrikulaId() {}

    public MatrikulaId(String ikasleNan, int ikasgaiId) {
        this.ikasleNan = ikasleNan;
        this.ikasgaiId = ikasgaiId;
    }

    public String getIkasleNan() { return ikasleNan; }
    public void setIkasleNan(String ikasleNan) { this.ikasleNan = ikasleNan; }

    public int getIkasgaiId() { return ikasgaiId; }
    public void setIkasgaiId(int ikasgaiId) { this.ikasgaiId = ikasgaiId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatrikulaId)) return false;
        MatrikulaId that = (MatrikulaId) o;
        return ikasgaiId == that.ikasgaiId && Objects.equals(ikasleNan, that.ikasleNan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ikasleNan, ikasgaiId);
    }
}