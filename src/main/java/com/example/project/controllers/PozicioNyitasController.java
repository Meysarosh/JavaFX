package com.example.project.controllers;

import com.example.project.api.ApiService;
import com.example.project.api.PriceData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PozicioNyitasController {

    @FXML public ComboBox<String> currencyPairDropdown;
    @FXML public Label closeoutBidLabel;
    @FXML public Label closeoutAskLabel;
    @FXML private TextField amountInput;
    @FXML private Label statusLabel;
    @FXML private Button vetelButton;
    @FXML private Button eladasButton;

    public void initializeDropdown() {
        currencyPairDropdown.getItems().addAll("EUR_USD", "USD_JPY", "GBP_USD", "USD_CHF");

        currencyPairDropdown.setOnAction(event -> {
            String selectedPair = currencyPairDropdown.getValue();
            if (selectedPair != null) {
                //Next function in this file
                fetchAndDisplayPrices(selectedPair);
            }
        });
    }

    private void fetchAndDisplayPrices(String currencyPair) {
        try {
            PriceData pricing = ApiService.fetchPricing(currencyPair);

            closeoutAskLabel.setText("Vetel: " + pricing.getCloseoutAsk());
            closeoutBidLabel.setText("Eladas" + pricing.getCloseoutBid());
            closeoutAskLabel.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            closeoutBidLabel.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleVetelClick() {
        processOrder("VETEL");
    }

    public void handleEladasClick() {
        processOrder("ELADAS");
    }

    private void processOrder(String actionType) {
        String currencyPair = currencyPairDropdown.getValue();
        String amount = amountInput.getText();

        if (amount == null || amount.isEmpty()) {
            statusLabel.setText("Kérjük, adjon meg egy összeget!");
            return;
        }

        try {
            String result = ApiService.placeOrder(actionType, amount, currencyPair);
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Sikeres művelet: " + result);
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Hiba: " + e.getMessage());
        }
    }
}
