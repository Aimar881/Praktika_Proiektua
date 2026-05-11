package org.example.noten.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Matrikulak")
public class Matrikula {

    @EmbeddedId
    private MatrikulaId id;

    @ManyToOne
    @MapsId("ikasleNan")
    @JoinColumn(name = "ikasle_nan")
    private Ikaslea ikaslea;

    @ManyToOne
    @MapsId("ikasgaiId")
    @JoinColumn(name = "ikasgai_id")
    private Ikasgaia ikasgaia;

    @Column(name = "matrikula_data")
    private LocalDate matrikulaData;

    public MatrikulaId getId() { return id; }
    public void setId(MatrikulaId id) { this.id = id; }

    public Ikaslea getIkaslea() { return ikaslea; }
    public void setIkaslea(Ikaslea ikaslea) { this.ikaslea = ikaslea; }

    public Ikasgaia getIkasgaia() { return ikasgaia; }
    public void setIkasgaia(Ikasgaia ikasgaia) { this.ikasgaia = ikasgaia; }

    public LocalDate getMatrikulaData() { return matrikulaData; }
    public void setMatrikulaData(LocalDate matrikulaData) { this.matrikulaData = matrikulaData; }
}