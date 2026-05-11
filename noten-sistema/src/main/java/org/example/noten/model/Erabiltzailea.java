package org.example.noten.model;

import javax.persistence.*;

@Entity
@Table(name = "Erabiltzaileak")
public class Erabiltzailea {

    @Id
    @Column(name = "nan", length = 9)
    private String nan;

    @Column(name = "izen_abizenak", nullable = false, length = 70)
    private String izenAbizenak;

    @Column(name = "tlfn_zenbakia", length = 9)
    private String tlfnZenbakia;

    @Column(name = "pasahitza", nullable = false, length = 255)
    private String pasahitza;

    @Enumerated(EnumType.STRING)
    @Column(name = "rola", nullable = false)
    private Rola rola;

    @Column(name = "aktibo")
    private boolean aktibo = true;

    // Enum de roles
    public enum Rola {
        irakasle, ikasle, tutore
    }

    // Getters y Setters
    public String getNan() { return nan; }
    public void setNan(String nan) { this.nan = nan; }

    public String getIzenAbizenak() { return izenAbizenak; }
    public void setIzenAbizenak(String izenAbizenak) { this.izenAbizenak = izenAbizenak; }

    public String getTlfnZenbakia() { return tlfnZenbakia; }
    public void setTlfnZenbakia(String tlfnZenbakia) { this.tlfnZenbakia = tlfnZenbakia; }

    public String getPasahitza() { return pasahitza; }
    public void setPasahitza(String pasahitza) { this.pasahitza = pasahitza; }

    public Rola getRola() { return rola; }
    public void setRola(Rola rola) { this.rola = rola; }

    public boolean isAktibo() { return aktibo; }
    public void setAktibo(boolean aktibo) { this.aktibo = aktibo; }
}