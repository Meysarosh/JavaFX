package com.example.project.utazasdb;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="helyseg")
public class Helyseg {
    @Id @GeneratedValue
    @Column(name="az")
    private int az;
    @Column(name = "nev")
    private String nev;
    @Column(name = "orszag")
    private String orszag;

    @OneToMany(mappedBy = "helyseg")
    private List<Szalloda> szalloda;

    public Helyseg() {
    }
    public Helyseg(int az, String nev, String orszag) {
        this.az = az;
        this.nev = nev;
        this.orszag = orszag;
    }

    public int getAz() {
        return az;
    }

    public void setAz(int az) {
        this.az = az;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getOrszag() {
        return orszag;
    }

    public void setOrszag(String orszag) {
        this.orszag = orszag;
    }

    public List<Szalloda> getSzalloda() {
        return szalloda;
    }

    public void setSzalloda(List<Szalloda> szalloda) {
        this.szalloda = szalloda;
    }
}