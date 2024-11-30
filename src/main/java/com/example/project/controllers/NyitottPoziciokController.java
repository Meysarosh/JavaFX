package com.example.project.controllers;

import com.example.project.api.Position;
import com.example.project.api.ApiService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;

public class NyitottPoziciokController {
    @FXML private TableView<Position> positionsTable;
    @FXML private TableColumn<Position, String> columnId;
    @FXML private TableColumn<Position, String> columnInstrument;
    @FXML private TableColumn<Position, String> columnUnits;
    @FXML private TableColumn<Position, String> columnPrice;
    @FXML private TableColumn<Position, String> columnPL;
    @FXML private TableColumn<Position, String> columnMargin;


    public void initialize() {
        columnId.setCellValueFactory(data -> {
            List<String> longIds = data.getValue().getLongPosition().getTradeIDs();
            List<String> shortIds = data.getValue().getShortPosition().getTradeIDs();
            String id = "";
            if (longIds == null) {
                id = shortIds.get(0);
            } else {
                id = longIds.get(0);
            }
            return new SimpleStringProperty(id);
        });

        columnInstrument.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getInstrument()));

        columnUnits.setCellValueFactory(data -> {
            String longUnits = data.getValue().getLongPosition().getUnits();
            String shortUnits = data.getValue().getShortPosition().getUnits();
            String units = String.valueOf(Integer.parseInt(longUnits) + Integer.parseInt(shortUnits));

            return new SimpleStringProperty(units);
        });

        columnPrice.setCellValueFactory(data -> {
            String longAvgPrice = data.getValue().getLongPosition().getAveragePrice();
            String shortAvgPrice = data.getValue().getShortPosition().getAveragePrice();
            String avgPrice = "";
            if (longAvgPrice == null) {
                avgPrice = shortAvgPrice;
            } else if (shortAvgPrice == null) {
                avgPrice = longAvgPrice;
            } else {
                avgPrice = String.valueOf((Integer.parseInt(longAvgPrice) + Integer.parseInt(shortAvgPrice))/2);
            }
            return new SimpleStringProperty(avgPrice);
        });

        columnPL.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUnrealizedPL()));

        columnMargin.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMarginUsed()));

    }

    public void loadPositions() {
        try {
            List<Position> positions = ApiService.fetchOpenPositions();
            positionsTable.getItems().setAll(positions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
