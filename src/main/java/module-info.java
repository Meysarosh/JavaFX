module com.example.project {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;

    // Java Persistence API and Hibernate
    requires java.persistence;
    requires org.hibernate.orm.core;

    // Additional modules for database and API
    requires java.sql;
    requires java.naming;

    // Gson for JSON parsing
    requires com.google.gson;

    opens com.example.project.api to com.google.gson;
    // Allow reflection-based frameworks (like Hibernate) to access specific packages
    opens com.example.project to javafx.fxml, org.hibernate.orm.core;
    exports com.example.project;

    opens com.example.project.utazasdb to javafx.fxml, org.hibernate.orm.core;
    exports com.example.project.utazasdb;

    opens com.example.project.controllers to javafx.fxml, org.hibernate.orm.core;
    exports com.example.project.controllers;
}
