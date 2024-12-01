package com.example.project.controllers;

import com.example.project.api.ApiService;
import com.example.project.api.ClosePositionResponse;
import com.example.project.api.Position;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PozicioZarasController {

    @FXML
    private ComboBox<String> idDropdown;

    @FXML
    private TextArea positionDetails;

    @FXML
    private Button closeButton;

    @FXML
    private Label statusLabel;

    private List<Position> positions = new ArrayList<>();

    @FXML
    public void initialize() {
        // Fetch positions from the API
        try {
            positions = ApiService.fetchOpenPositions(); // Fetch positions via API
            List<String> ids = extractTradeIds(positions);
            idDropdown.setItems(FXCollections.observableArrayList(ids));

            // Set a listener for dropdown selection
            idDropdown.setOnAction(e -> {
                String selectedId = idDropdown.getValue();
                showPositionDetails(selectedId);
            });

        } catch (Exception e) {
            statusLabel.setText("Hiba az adatok betöltésekor: " + e.getMessage());
        }
    }

    private List<String> extractTradeIds(List<Position> positions) {
        List<String> ids = new ArrayList<>();
        for (Position position : positions) {
            List<String> longIds = position.getLongPosition().getTradeIDs();
            List<String> shortIds = position.getShortPosition().getTradeIDs();
            if (longIds != null) {
                ids.addAll(longIds);
            }
            if (shortIds != null) {
                ids.addAll(shortIds);
            }
        }
        return ids;
    }

    private void showPositionDetails(String tradeId) {
        for (Position position : positions) {
            if (positionContainsTradeId(position, tradeId)) {
                positionDetails.setText(formatPositionDetails(position));
                return;
            }
        }
        positionDetails.setText("Nem található pozíció az adott ID-vel: " + tradeId);
    }

    private boolean positionContainsTradeId(Position position, String tradeId) {
        return (position.getLongPosition().getTradeIDs() != null && position.getLongPosition().getTradeIDs().contains(tradeId)) ||
                (position.getShortPosition().getTradeIDs() != null && position.getShortPosition().getTradeIDs().contains(tradeId));
    }

    private String formatPositionDetails(Position position) {
        StringBuilder details = new StringBuilder();
        details.append("Instrument: ").append(position.getInstrument()).append("\n");

        if (!Objects.equals(position.getLongPosition().getUnits(), "0")) {
            details.append("Long pozíció:\n");
            details.append("  Units: ").append(position.getLongPosition().getUnits()).append("\n");
            details.append("  Átlagos Ár: ").append(position.getLongPosition().getAveragePrice()).append("\n");
            details.append("  PL: ").append(position.getUnrealizedPL()).append("\n");
        }

        if (!Objects.equals(position.getShortPosition().getUnits(), "0")) {
            details.append("Short pozíció:\n");
            details.append("  Egységek: ").append(position.getShortPosition().getUnits()).append("\n");
            details.append("  Átlagos Ár: ").append(position.getShortPosition().getAveragePrice()).append("\n");
            details.append("  PL: ").append(position.getUnrealizedPL()).append("\n");
        }

        return details.toString();
    }

    @FXML
    private void handleClosePosition() {
        System.out.println("BtnClicked");
        String selectedId = idDropdown.getValue();
        if (selectedId == null) {
            statusLabel.setText("Kérjük, válassz egy ID-t!");
            return;
        }

        for (Position position : positions) {
            if (positionContainsTradeId(position, selectedId)) {

                String instrument = position.getInstrument();
                boolean isLong = position.getLongPosition().getTradeIDs() != null &&
                        position.getLongPosition().getTradeIDs().contains(selectedId);

                try {
                    ClosePositionResponse response = ApiService.closePosition(instrument, isLong ? "long" : "short");

                    if (response.getLongOrderFillTransaction() != null) {

                        statusLabel.setStyle("-fx-text-fill: green;");
                        statusLabel.setText("Sikeresen bezárva az ID: " + selectedId + "\nindíték: " + response.getLongOrderFillTransaction().getReason());
                        idDropdown.getItems().remove(selectedId);
                        positionDetails.clear();

                    } else if (response.getShortOrderFillTransaction() != null) {

                        statusLabel.setStyle("-fx-text-fill: green;");
                        statusLabel.setText("Sikeresen bezárva az ID: " + selectedId + "\nindíték: " + response.getShortOrderFillTransaction().getReason());
                        idDropdown.getItems().remove(selectedId);
                        positionDetails.clear();

                    } else if (response.getLongOrderCancelTransaction() != null) {

                        statusLabel.setStyle("-fx-text-fill: red;");
                        statusLabel.setText("Pozíció zárás elutasítva" + "\nindíték: " + response.getLongOrderCancelTransaction().getReason());

                    } else if (response.getShortOrderCancelTransaction() != null) {

                        statusLabel.setStyle("-fx-text-fill: red;");
                        statusLabel.setText("Pozíció zárás elutasítva" + "\nindíték: " + response.getShortOrderCancelTransaction().getReason());

                    }
                    return;
                } catch (Exception e) {
                    statusLabel.setText("Hiba a pozíció zárásakor: " + e.getMessage());
                    return;
                }
            }
        }
    }
}
