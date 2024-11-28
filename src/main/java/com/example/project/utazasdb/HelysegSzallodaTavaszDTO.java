package com.example.project.utazasdb;

public class HelysegSzallodaTavaszDTO {
    private String helysegNev;
    private String helysegOrszag;
    private String szallodaNev;
    private int szallodaBesorolas;
    private String tavaszIndulas;
    private int tavaszIdotartam;
    private int tavaszAr;
    private int szallodaTengerpartTav;

    public HelysegSzallodaTavaszDTO(String helysegNev, String helysegOrszag, String szallodaNev, int szallodaBesorolas, String tavaszIndulas, int tavaszIdotartam, int tavaszAr, int szallodaTengerpartTav) {
        this.helysegNev = helysegNev;
        this.helysegOrszag = helysegOrszag;
        this.szallodaNev = szallodaNev;
        this.szallodaBesorolas = szallodaBesorolas;
        this.tavaszIndulas = tavaszIndulas;
        this.tavaszIdotartam = tavaszIdotartam;
        this.tavaszAr = tavaszAr;
        this.szallodaTengerpartTav = szallodaTengerpartTav;
    }

    public String getHelysegNev() {
        return helysegNev;
    }

    public void setHelysegNev(String helysegNev) {
        this.helysegNev = helysegNev;
    }

    public String getHelysegOrszag() {
        return helysegOrszag;
    }

    public void setHelysegOrszag(String helysegOrszag) {
        this.helysegOrszag = helysegOrszag;
    }

    public String getSzallodaNev() {
        return szallodaNev;
    }

    public void setSzallodaNev(String szallodaNev) {
        this.szallodaNev = szallodaNev;
    }

    public int getSzallodaBesorolas() {
        return szallodaBesorolas;
    }

    public void setSzallodaBesorolas(int szallodaBesorolas) {
        this.szallodaBesorolas = szallodaBesorolas;
    }

    public String getTavaszIndulas() {
        return tavaszIndulas;
    }

    public void setTavaszIndulas(String tavaszIndulas) {
        this.tavaszIndulas = tavaszIndulas;
    }

    public int getTavaszIdotartam() {
        return tavaszIdotartam;
    }

    public void setTavaszIdotartam(int tavaszIdotartam) {
        this.tavaszIdotartam = tavaszIdotartam;
    }

    public int getTavaszAr() {
        return tavaszAr;
    }

    public void setTavaszAr(int tavaszAr) {
        this.tavaszAr = tavaszAr;
    }

    public int getSzallodaTengerpartTav() {
        return szallodaTengerpartTav;
    }

    public void setSzallodaTengerpartTav(int szallodaTengerpartTav) {
        this.szallodaTengerpartTav = szallodaTengerpartTav;
    }

}
