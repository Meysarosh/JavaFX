package com.example.project.controllers;

import com.example.project.utazasdb.Szalloda;
import com.example.project.utazasdb.Tavasz;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class AdatbazisCreateController {
    @FXML private ComboBox<String> cbSzalloda;
    @FXML private TextField tfIndulas;
    @FXML private TextField tfIdotartam;
    @FXML private TextField tfAr;
    @FXML private Label lblMessage;

    private SessionFactory factory;

    @FXML
    public void initialize() {
        factory = new Configuration().configure().buildSessionFactory();

        try (Session session = factory.openSession()) {
            List<Szalloda> szallodak = session.createQuery("FROM Szalloda", Szalloda.class).list();
            for (Szalloda sz : szallodak) {
                cbSzalloda.getItems().add(sz.getNev());
            }
        }
    }

    @FXML
    public void onAddRecordClick() {
        lblMessage.setText("");
        String szallodaNev = cbSzalloda.getValue();
        String indulas = tfIndulas.getText();
        int idotartam = Integer.parseInt(tfIdotartam.getText());
        int ar = Integer.parseInt(tfAr.getText());

        if (szallodaNev == null || indulas.isEmpty() || tfIdotartam.getText().isEmpty() || tfAr.getText().isEmpty()) {
            lblMessage.setText("Minden mezőt ki kell tölteni!");
            return;
        }

        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Szalloda szalloda = session.createQuery("FROM Szalloda WHERE nev = :nev", Szalloda.class)
                    .setParameter("nev", szallodaNev)
                    .uniqueResult();

            if (szalloda != null) {
                Tavasz tavasz = new Tavasz();
                tavasz.setSzalloda_az(szalloda.getAz());
                tavasz.setIndulas(indulas);
                tavasz.setIdotartam(idotartam);
                tavasz.setAr(ar);

                session.save(tavasz);
                transaction.commit();

                tfIndulas.clear();
                tfIdotartam.clear();
                tfAr.clear();
                cbSzalloda.setValue(null);

                lblMessage.setStyle("-fx-text-fill: green;");
                lblMessage.setText("Sikeres mentés!");
            } else {
                lblMessage.setStyle("-fx-text-fill: red;");
                lblMessage.setText("Hiba: Szálloda nem található.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
