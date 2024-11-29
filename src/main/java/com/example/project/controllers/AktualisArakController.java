package com.example.project.controllers;

import com.example.project.api.ApiService;
import com.example.project.api.PriceData;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AktualisArakController {

    @FXML
    private ComboBox<String> currencyPairDropdown;

    @FXML
    private TableView<PriceData> priceTable;

    @FXML
    private TableColumn<PriceData, String> columnInstrument;

    @FXML
    private TableColumn<PriceData, String> columnCloseoutAsk;

    @FXML
    private TableColumn<PriceData, String> columnCloseoutBid;
    @FXML
    Label closeoutAskLabel;
    @FXML
    Label closeoutBidLabel;

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
}
