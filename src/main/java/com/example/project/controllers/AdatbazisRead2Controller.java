package com.example.project.controllers;

import com.example.project.utazasdb.Helyseg;
import com.example.project.utazasdb.HelysegSzallodaTavaszDTO;
import com.example.project.utazasdb.Tavasz;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class AdatbazisRead2Controller {

    @FXML private TableView<HelysegSzallodaTavaszDTO> tvData2;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, String> colHelysegNev;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, String> colHelysegOrszag;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, String> colSzallodaNev;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, Integer> colSzallodaBesorolas;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, String> colTavaszIndulas;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, Integer> colTavaszIdotartam;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, Integer> colTavaszAr;
    @FXML private TableColumn<HelysegSzallodaTavaszDTO, Integer> colTengerpartTav;
    @FXML private ComboBox<String> cbHelyseg;
    @FXML private TextField tfSearch;
    @FXML private RadioButton rb1, rb2, rb3, rb4, rb5;
    @FXML private CheckBox cbTengerpart;

    @FXML
    public void initialize() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        try (Session session = factory.openSession()) {
            List<Helyseg> helysegek = session.createQuery("FROM Helyseg", Helyseg.class).list();
            cbHelyseg.getItems().addAll(helysegek.stream().map(Helyseg::getNev).toList());
        }

        ToggleGroup group = new ToggleGroup();
        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        rb4.setToggleGroup(group);
        rb5.setToggleGroup(group);
    }

    @FXML
    private void onSearchClick() {
        String helysegNev = cbHelyseg.getValue();
        String searchText = tfSearch.getText();
        Integer besorolas = null;

        if (rb1.isSelected()) besorolas = 1;
        if (rb2.isSelected()) besorolas = 2;
        if (rb3.isSelected()) besorolas = 3;
        if (rb4.isSelected()) besorolas = 4;
        if (rb5.isSelected()) besorolas = 5;

        boolean tengerpart = cbTengerpart.isSelected();

        filterDatabase(helysegNev, searchText, besorolas, tengerpart);
    }

    public void filterDatabase(String helysegNev, String searchText, Integer besorolas, boolean tengerpart) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        try (Session session = factory.openSession()) {

            StringBuilder hql = new StringBuilder("FROM Tavasz t WHERE 1=1");

            if (helysegNev != null && !helysegNev.isEmpty()) hql.append(" AND t.szalloda.helyseg.nev = :helysegNev");
            if (searchText != null && !searchText.isEmpty()) hql.append(" AND t.szalloda.nev LIKE :searchText");
            if (besorolas != null) hql.append(" AND t.szalloda.besorolas = :besorolas");
            if (tengerpart) hql.append(" AND t.szalloda.tengerpart_tav = 0");

            var query = session.createQuery(hql.toString(), Tavasz.class);
            if (helysegNev != null && !helysegNev.isEmpty()) query.setParameter("helysegNev", helysegNev);
            if (searchText != null && !searchText.isEmpty()) query.setParameter("searchText", "%" + searchText + "%");
            if (besorolas != null) query.setParameter("besorolas", besorolas);

            List<Tavasz> results = query.list();

            results.forEach(t -> {
                Hibernate.initialize(t.getSzalloda());
                Hibernate.initialize(t.getSzalloda().getHelyseg());
            });

            List<HelysegSzallodaTavaszDTO> data = results.stream().map(t -> new HelysegSzallodaTavaszDTO(
                    t.getSzalloda().getHelyseg().getNev(),
                    t.getSzalloda().getHelyseg().getOrszag(),
                    t.getSzalloda().getNev(),
                    t.getSzalloda().getBesorolas(),
                    t.getIndulas(),
                    t.getIdotartam(),
                    t.getAr(),
                    t.getSzalloda().getTengerpart_tav()
            )).toList();

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

            tvData2.getItems().setAll(data);
            tvData2.getColumns().add(colDummy);
        }
    }

}
