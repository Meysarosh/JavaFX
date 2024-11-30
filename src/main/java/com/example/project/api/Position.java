package com.example.project.api;

import com.google.gson.annotations.SerializedName;

public class Position {
    @SerializedName("instrument")
    private String instrument;
    @SerializedName("long")
    private PositionDetails longPosition;
    @SerializedName("short")
    private PositionDetails shortPosition;
    @SerializedName("unrealizedPL")
    private String unrealizedPL;
    @SerializedName("marginUsed")
    private String marginUsed;

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public PositionDetails getLongPosition() {
        return longPosition;
    }

    public void setLongPosition(PositionDetails longPosition) {
        this.longPosition = longPosition;
    }

    public PositionDetails getShortPosition() {
        return shortPosition;
    }

    public void setShortPosition(PositionDetails shortPosition) {
        this.shortPosition = shortPosition;
    }

    public String getUnrealizedPL() {
        return unrealizedPL;
    }

    public void setUnrealizedPL(String unrealizedPL) {
        this.unrealizedPL = unrealizedPL;
    }

    public String getMarginUsed() {
        return marginUsed;
    }

    public void setMarginUsed(String marginUsed) {
        this.marginUsed = marginUsed;
    }
}
