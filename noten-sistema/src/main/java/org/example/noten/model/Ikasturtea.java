package org.example.noten.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Ikasturteak")
public class Ikasturtea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "izena", length = 10, unique = true)
    private String izena;

    @Column(name = "hasiera")
    private LocalDate hasiera;

    @Column(name = "bukaera")
    private LocalDate bukaera;

    @Column(name = "aktibo")
    private boolean aktibo = false;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIzena() { return izena; }
    public void setIzena(String izena) { this.izena = izena; }

    public LocalDate getHasiera() { return hasiera; }
    public void setHasiera(LocalDate hasiera) { this.hasiera = hasiera; }

    public LocalDate getBukaera() { return bukaera; }
    public void setBukaera(LocalDate bukaera) { this.bukaera = bukaera; }

    public boolean isAktibo() { return aktibo; }
    public void setAktibo(boolean aktibo) { this.aktibo = aktibo; }
}