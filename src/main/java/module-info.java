module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;

    opens com.example.project to javafx.fxml, org.hibernate.orm.core;
    exports com.example.project;
    exports com.example.project.utazasdb;
    opens com.example.project.utazasdb to javafx.fxml, org.hibernate.orm.core;
    exports com.example.project.controllers;
    opens com.example.project.controllers to javafx.fxml, org.hibernate.orm.core;

}