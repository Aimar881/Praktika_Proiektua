package org.example.noten.model;

import javax.persistence.*;

@Entity
@Table(name = "Ikasleak")
public class Ikaslea {

    @Id
    @Column(name = "nan", length = 9)
    private String nan;

    @OneToOne
    @MapsId
    @JoinColumn(name = "nan")
    private Erabiltzailea erabiltzailea;

    @Column(name = "taldea", length = 10)
    private String taldea;

    // Getters y Setters
    public String getNan() { return nan; }
    public void setNan(String nan) { this.nan = nan; }

    public Erabiltzailea getErabiltzailea() { return erabiltzailea; }
    public void setErabiltzailea(Erabiltzailea erabiltzailea) { this.erabiltzailea = erabiltzailea; }

    public String getTaldea() { return taldea; }
    public void setTaldea(String taldea) { this.taldea = taldea; }
}