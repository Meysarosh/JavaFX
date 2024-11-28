package com.example.project.utazasdb;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="szalloda")
public class Szalloda {
    @Id
    private String az;
    @Column(name = "nev")
    private String nev;
    @Column(name = "besorolas")
    private int besorolas;
    @Column(name = "helyseg_az")
    private int helyseg_az;
    @Column(name = "tengerpart_tav")
    private int tengerpart_tav;
    @Column(name = "repter_tav")
    private int repter_tav;
    @Column(name = "felpanzio")
    private boolean felpanzio;

    @OneToMany(mappedBy = "szalloda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tavasz> tavasz;

    @ManyToOne
    @JoinColumn(name = "helyseg_az", referencedColumnName = "az", insertable = false, updatable = false)
    private Helyseg helyseg;

    public Szalloda() {
    }

    public Szalloda(String az, boolean felpanzio, int repter_tav, int tengerpart_tav, int besorolas, int helyseg_az, String nev) {
        this.az = az;
        this.felpanzio = felpanzio;
        this.repter_tav = repter_tav;
        this.tengerpart_tav = tengerpart_tav;
        this.besorolas = besorolas;
        this.helyseg_az = helyseg_az;
        this.nev = nev;
    }

    public String getAz() {
        return az;
    }

    public void setAz(String az) {
        this.az = az;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getBesorolas() {
        return besorolas;
    }

    public void setBesorolas(int besorolas) {
        this.besorolas = besorolas;
    }

    public int getHelyseg_az() {
        return helyseg_az;
    }

    public void setHelyseg_az(int helyseg_az) {
        this.helyseg_az = helyseg_az;
    }

    public int getTengerpart_tav() {
        return tengerpart_tav;
    }

    public void setTengerpart_tav(int tengerpart_tav) {
        this.tengerpart_tav = tengerpart_tav;
    }

    public int getRepter_tav() {
        return repter_tav;
    }

    public void setRepter_tav(int repter_tav) {
        this.repter_tav = repter_tav;
    }

    public boolean isFelpanzio() {
        return felpanzio;
    }

    public void setFelpanzio(boolean felpanzio) {
        this.felpanzio = felpanzio;
    }

    public List<Tavasz> getTavasz() {
        return tavasz;
    }

    public void setTavasz(List<Tavasz> tavasz) {
        this.tavasz = tavasz;
    }

    public Helyseg getHelyseg() {
        return helyseg;
    }

    public void setHelyseg(Helyseg helyseg) {
        this.helyseg = helyseg;
    }
}