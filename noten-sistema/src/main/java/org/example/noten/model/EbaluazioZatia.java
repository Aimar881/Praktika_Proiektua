package org.example.noten.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "Izena ezin da hutsik egon")
    private String izena;

    @Column(name = "pisua")
    @DecimalMin(value = "0.1", message = "Pisuak 0.1 baino handiagoa izan behar du")
    @DecimalMax(value = "1.0", message = "Pisuak 1.0 baino txikiagoa izan behar du")
    private double pisua;

    @Column(name = "ebaluazio_zenbakia")
    private int ebaluazioZenbakia;

    @OneToMany(mappedBy = "ebaluazioZatia")
    private List<Nota> notak;

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