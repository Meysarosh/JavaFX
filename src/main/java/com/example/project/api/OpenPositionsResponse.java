package com.example.project.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OpenPositionsResponse {
    @SerializedName("positions")
    private List<Position> positions;

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}

