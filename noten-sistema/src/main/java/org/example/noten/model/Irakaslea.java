package org.example.noten.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Irakasleak")
public class Irakaslea {

    @Id
    @Column(name = "nan", length = 9)
    private String nan;

    @OneToOne
    @MapsId
    @JoinColumn(name = "nan")
    private Erabiltzailea erabiltzailea;

    @OneToMany(mappedBy = "irakaslea")
    private List<Ikasgaia> ikasgaiak;

    // Getters y Setters
    public String getNan() { return nan; }
    public void setNan(String nan) { this.nan = nan; }

    public Erabiltzailea getErabiltzailea() { return erabiltzailea; }
    public void setErabiltzailea(Erabiltzailea erabiltzailea) { this.erabiltzailea = erabiltzailea; }

    public List<Ikasgaia> getIkasgaiak() { return ikasgaiak; }
    public void setIkasgaiak(List<Ikasgaia> ikasgaiak) { this.ikasgaiak = ikasgaiak; }
}