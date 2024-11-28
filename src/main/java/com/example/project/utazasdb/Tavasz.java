package com.example.project.utazasdb;
import javax.persistence.*;

@Entity
@Table(name="tavasz")
public class Tavasz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sorszam;
    @Column(name = "szalloda_az")
    private String szalloda_az;
    @Column(name = "indulas")
    private String indulas;
    @Column(name = "idotartam")
    private int idotartam;
    @Column(name = "ar")
    private int ar;

    @ManyToOne
    @JoinColumn(name = "szalloda_az", referencedColumnName = "az", insertable = false, updatable = false)
    private Szalloda szalloda;

    public Tavasz() {
    }

    public Tavasz(int sorszam, String szalloda_az, String indulas, int idotartam, int ar) {
        this.sorszam = sorszam;
        this.szalloda_az = szalloda_az;
        this.indulas = indulas;
        this.idotartam = idotartam;
        this.ar = ar;
    }

    public int getSorszam() {
        return sorszam;
    }

    public void setSorszam(int sorszam) {
        this.sorszam = sorszam;
    }

    public String getSzalloda_az() {
        return szalloda_az;
    }

    public void setSzalloda_az(String szalloda_az) {
        this.szalloda_az = szalloda_az;
    }

    public String getIndulas() {
        return indulas;
    }

    public void setIndulas(String indulas) {
        this.indulas = indulas;
    }

    public int getIdotartam() {
        return idotartam;
    }

    public void setIdotartam(int idotartam) {
        this.idotartam = idotartam;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public Szalloda getSzalloda() {
        return szalloda;
    }

    public void setSzalloda(Szalloda szalloda) {
        this.szalloda = szalloda;
    }
}