package com.example.project.controllers;

import com.example.project.api.AccountSummary;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.beans.property.SimpleStringProperty;

public class SzamlainformaciokController {

    @FXML private TableView<AccountSummary> accountTable;
    @FXML private TableColumn<AccountSummary, String> columnAlias;
    @FXML private TableColumn<AccountSummary, String> columnBalance;
    @FXML private TableColumn<AccountSummary, String> columnCurrency;
    @FXML private TableColumn<AccountSummary, String> columnMarginRate;
    @FXML private TableColumn<AccountSummary, String> columnPL;
    @FXML private TableColumn<AccountSummary, String> columnNAV;

    public void initialize() {
        columnAlias.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAlias()));
        columnBalance.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBalance()));
        columnCurrency.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurrency()));
        columnMarginRate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMarginRate()));
        columnPL.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPl()));
        columnNAV.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNav()));
    }

    public void populateAccountSummary(AccountSummary summary) {
        accountTable.getItems().clear();
        accountTable.getItems().add(summary);
    }
}
