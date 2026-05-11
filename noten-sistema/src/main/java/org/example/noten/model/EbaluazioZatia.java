package org.example.noten.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Ebaluazio_Zatiak")
public class EbaluazioZatia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ikasgai_id")
    private Ikasgaia ikasgaia;

    @Column(name = "izena", length = 100)
    private String izena;

    @Column(name = "pisua")
    private double pisua;

    @Column(name = "ebaluazio_zenbakia")
    private int ebaluazioZenbakia;

    @OneToMany(mappedBy = "ebaluazioZatia")
    private List<Nota> notak;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Ikasgaia getIkasgaia() { return ikasgaia; }
    public void setIkasgaia(Ikasgaia ikasgaia) { this.ikasgaia = ikasgaia; }

    public String getIzena() { return izena; }
    public void setIzena(String izena) { this.izena = izena; }

    public double getPisua() { return pisua; }
    public void setPisua(double pisua) { this.pisua = pisua; }

    public int getEbaluazioZenbakia() { return ebaluazioZenbakia; }
    public void setEbaluazioZenbakia(int ebaluazioZenbakia) { this.ebaluazioZenbakia = ebaluazioZenbakia; }

    public List<Nota> getNotak() { return notak; }
    public void setNotak(List<Nota> notak) { this.notak = notak; }
}