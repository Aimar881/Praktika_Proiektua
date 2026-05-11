package org.example.noten.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Ikasgaiak")
public class Ikasgaia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "izena", length = 100)
    private String izena;

    @ManyToOne
    @JoinColumn(name = "irakasle_nan")
    private Irakaslea irakaslea;

    @ManyToOne
    @JoinColumn(name = "ikasturte_id")
    private Ikasturtea ikasturtea;

    @OneToMany(mappedBy = "ikasgaia")
    private List<EbaluazioZatia> zatiak;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIzena() { return izena; }
    public void setIzena(String izena) { this.izena = izena; }

    public Irakaslea getIrakaslea() { return irakaslea; }
    public void setIrakaslea(Irakaslea irakaslea) { this.irakaslea = irakaslea; }

    public Ikasturtea getIkasturtea() { return ikasturtea; }
    public void setIkasturtea(Ikasturtea ikasturtea) { this.ikasturtea = ikasturtea; }

    public List<EbaluazioZatia> getZatiak() { return zatiak; }
    public void setZatiak(List<EbaluazioZatia> zatiak) { this.zatiak = zatiak; }
}