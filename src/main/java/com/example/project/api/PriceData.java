package com.example.project.api;

public class PriceData {
    private String instrument;
    private String closeoutAsk;
    private String closeoutBid;

    public PriceData(String instrument, String closeoutAsk, String closeoutBid) {
        this.instrument = instrument;
        this.closeoutAsk = closeoutAsk;
        this.closeoutBid = closeoutBid;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getCloseoutBid() {
        return closeoutBid;
    }

    public void setCloseoutBid(String closeoutBid) {
        this.closeoutBid = closeoutBid;
    }

    public String getCloseoutAsk() {
        return closeoutAsk;
    }

    public void setCloseoutAsk(String closeoutAsk) {
        this.closeoutAsk = closeoutAsk;
    }
}
