package com.example.project.controllers;

import com.example.project.utazasdb.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class AdatbazisReadController {
    @FXML private TableView<HelysegSzallodaTavaszDTO> tvData;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, String> colHelysegNev;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, String> colHelysegOrszag;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, String> colSzallodaNev;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, Integer> colSzallodaBesorolas;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, String> colTavaszIndulas;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, Integer> colTavaszIdotartam;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, Integer> colTavaszAr;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, Integer> colTengerpartTav;

    public void readDatabase() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        try (Session session = factory.openSession()) {
            List<Tavasz> tavaszList = session.createQuery("FROM Tavasz", Tavasz.class)
                    .list();

            tavaszList.forEach(t -> {
                Hibernate.initialize(t.getSzalloda());
                Hibernate.initialize(t.getSzalloda().getHelyseg());
            });

            List<HelysegSzallodaTavaszDTO> data = tavaszList.stream().map(t -> new HelysegSzallodaTavaszDTO(
                    t.getSzalloda().getHelyseg().getNev(),
                    t.getSzalloda().getHelyseg().getOrszag(),
                    t.getSzalloda().getNev(),
                    t.getSzalloda().getBesorolas(),
                    t.getIndulas(),
                    t.getIdotartam(),
                    t.getAr(),
                    t.getSzalloda().getTengerpart_tav()
            )).sorted(Comparator.comparing(HelysegSzallodaTavaszDTO::getTavaszIndulas).reversed())
                    .toList();

            TableColumn<HelysegSzallodaTavaszDTO, String> colDummy = new TableColumn<>(" ");
            colDummy.setPrefWidth(15);

            colHelysegNev.setCellValueFactory(new PropertyValueFactory<>("helysegNev"));
            colHelysegOrszag.setCellValueFactory(new PropertyValueFactory<>("helysegOrszag"));
            colSzallodaNev.setCellValueFactory(new PropertyValueFactory<>("szallodaNev"));
            colSzallodaBesorolas.setCellValueFactory(new PropertyValueFactory<>("szallodaBesorolas"));
            colTavaszIndulas.setCellValueFactory(new PropertyValueFactory<>("tavaszIndulas"));
            colTavaszIdotartam.setCellValueFactory(new PropertyValueFactory<>("tavaszIdotartam"));
            colTavaszAr.setCellValueFactory(new PropertyValueFactory<>("tavaszAr"));
            colTengerpartTav.setCellValueFactory(new PropertyValueFactory<>("szallodaTengerpartTav"));

            tvData.getItems().setAll(data);
            tvData.getColumns().add(colDummy);
        }
    }
}
