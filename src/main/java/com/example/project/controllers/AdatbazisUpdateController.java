package com.example.project.controllers;

import com.example.project.utazasdb.Szalloda;
import com.example.project.utazasdb.Tavasz;
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

public class AdatbazisUpdateController {
    @FXML private ComboBox<Integer> cbTavasz;
    @FXML private ComboBox<String> cbSzalloda;
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

            List<Szalloda> szallodak = session.createQuery("FROM Szalloda", Szalloda.class).list();
            for (Szalloda sz : szallodak) {
                cbSzalloda.getItems().add(sz.getNev());
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
                cbSzalloda.setValue(tavasz.getSzalloda().getNev());
                tfIndulas.setText(tavasz.getIndulas());
                tfIdotartam.setText(String.valueOf(tavasz.getIdotartam()));
                tfAr.setText(String.valueOf(tavasz.getAr()));
            } else {
                lblMessage.setText("Rekord nem található!");
            }
        }
    }

    @FXML
    public void onSaveRecordClick() {
        lblMessage.setText("");

        try {
            Integer sorszam = cbTavasz.getValue();
            String szallodaNev = cbSzalloda.getValue();
            String indulas = tfIndulas.getText();
            int idotartam = Integer.parseInt(tfIdotartam.getText());
            int ar = Integer.parseInt(tfAr.getText());

            if (sorszam == null || szallodaNev == null || indulas.isEmpty()) {
                lblMessage.setText("Minden mezőt ki kell tölteni!");
                return;
            }

            try (Session session = factory.openSession()) {
                Transaction transaction = session.beginTransaction();

                Tavasz tavasz = session.get(Tavasz.class, sorszam);
                if (tavasz != null) {
                    Szalloda szalloda = session.createQuery("FROM Szalloda WHERE nev = :nev", Szalloda.class)
                            .setParameter("nev", szallodaNev)
                            .uniqueResult();

                    if (szalloda != null) {
                        tavasz.setSzalloda_az(szalloda.getAz());
                        tavasz.setIndulas(indulas);
                        tavasz.setIdotartam(idotartam);
                        tavasz.setAr(ar);

                        session.update(tavasz);
                        transaction.commit();

                        lblMessage.setStyle("-fx-text-fill: green;");
                        lblMessage.setText("Rekord frissítve!");
                    } else {
                        lblMessage.setStyle("-fx-text-fill: red;");
                        lblMessage.setText("Hiba: Szálloda nem található!");
                    }
                } else {
                    lblMessage.setStyle("-fx-text-fill: red;");
                    lblMessage.setText("Hiba: Rekord nem található!");
                }
            }
        } catch (NumberFormatException e) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Hiba: Érvénytelen adat!");
        } catch (Exception e) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Hiba történt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

