package com.example.project.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PositionDetails {
    @SerializedName("units")
    private String units;
    @SerializedName("averagePrice")
    private String averagePrice;
    @SerializedName("tradeIDs")
    private List<String> tradeIDs;

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public List<String> getTradeIDs() {
        return tradeIDs;
    }

    public void setTradeIDs(List<String> tradeIDs) {
        this.tradeIDs = tradeIDs;
    }
}
