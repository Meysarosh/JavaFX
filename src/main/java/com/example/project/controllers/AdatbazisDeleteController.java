package com.example.project.controllers;

import com.example.project.utazasdb.Tavasz;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class AdatbazisDeleteController {
    @FXML private ComboBox<Integer> cbTavasz;
    @FXML private TextField tfSzalloda;
    @FXML private TextField tfIndulas;
    @FXML private TextField tfIdotartam;
    @FXML private TextField tfAr;
    @FXML private Label lblMessage;

    private SessionFactory factory;

    @FXML
    public void initialize() {
        factory = new Configuration().configure().buildSessionFactory();

        cbTavasz.setVisibleRowCount(15); // Limit visible rows to 15

        cbTavasz.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : "Sorszám: " + item);
            }
        });

        try (Session session = factory.openSession()) {
            List<Tavasz> tavaszList = session.createQuery("FROM Tavasz", Tavasz.class).list();
            for (Tavasz t : tavaszList) {
                cbTavasz.getItems().add(t.getSorszam());
            }
        }

        cbTavasz.setOnAction(event -> loadTavaszRecord(cbTavasz.getValue()));
    }

    private void loadTavaszRecord(Integer sorszam) {
        lblMessage.setText("");
        if (sorszam == null) return;

        try (Session session = factory.openSession()) {
            Tavasz tavasz = session.get(Tavasz.class, sorszam);

            if (tavasz != null) {
                tfSzalloda.setText(tavasz.getSzalloda().getNev());
                tfIndulas.setText(tavasz.getIndulas());
                tfIdotartam.setText(String.valueOf(tavasz.getIdotartam()));
                tfAr.setText(String.valueOf(tavasz.getAr()));
            } else {
                lblMessage.setText("Rekord nem található!");
            }
        }
    }

    @FXML
    public void onDeleteRecordClick() {
        lblMessage.setText("");

        try {
            Integer sorszam = cbTavasz.getValue();

            if (sorszam == null) {
                lblMessage.setText("Válassz egy rekordot!");
                return;
            }

            try (Session session = factory.openSession()) {
                Transaction transaction = session.beginTransaction();

                Tavasz tavasz = session.get(Tavasz.class, sorszam);
                if (tavasz != null) {
                    session.delete(tavasz);
                    transaction.commit();

                    Platform.runLater(() -> {
                        lblMessage.setStyle("-fx-text-fill: green;");
                        lblMessage.setText("Rekord törölve!");
                    });

                    cbTavasz.getItems().remove(sorszam);
                    cbTavasz.setValue(null);
                    tfSzalloda.clear();
                    tfIndulas.clear();
                    tfIdotartam.clear();
                    tfAr.clear();
                } else {
                    lblMessage.setStyle("-fx-text-fill: red;");
                    lblMessage.setText("Hiba: Rekord nem található!");
                }
            }
        } catch (Exception e) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Hiba történt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
