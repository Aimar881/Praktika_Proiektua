package org.example.noten.model;

import javax.persistence.*;

@Entity
@Table(name = "Notak")
public class Nota {

    @EmbeddedId
    private NotaId id;

    @ManyToOne
    @MapsId("ikasleNan")
    @JoinColumn(name = "ikasle_nan")
    private Ikaslea ikaslea;

    @ManyToOne
    @MapsId("zatiId")
    @JoinColumn(name = "zati_id")
    private EbaluazioZatia ebaluazioZatia;

    @Column(name = "nota_zenbakia")
    private double notaZenbakia;

    // Getters y Setters
    public NotaId getId() { return id; }
    public void setId(NotaId id) { this.id = id; }

    public Ikaslea getIkaslea() { return ikaslea; }
    public void setIkaslea(Ikaslea ikaslea) { this.ikaslea = ikaslea; }

    public EbaluazioZatia getEbaluazioZatia() { return ebaluazioZatia; }
    public void setEbaluazioZatia(EbaluazioZatia ebaluazioZatia) { this.ebaluazioZatia = ebaluazioZatia; }

    public double getNotaZenbakia() { return notaZenbakia; }
    public void setNotaZenbakia(double notaZenbakia) { this.notaZenbakia = notaZenbakia; }
}