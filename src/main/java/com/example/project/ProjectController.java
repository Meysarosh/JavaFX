package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.function.Consumer;

public class ProjectController {
    @FXML
    private Pane placeholder;

    public void adatbazisReadClick() {
        loadView("adatbazis-read.fxml", controller -> {
            if (controller instanceof com.example.project.controllers.AdatbazisReadController adatbazisReadController) {
                adatbazisReadController.readDatabase();
            }
        });
    }

    public void adatbazisReadClick2() {
        loadView("adatbazis-read2.fxml", controller -> {
            if (controller instanceof com.example.project.controllers.AdatbazisRead2Controller adatbazisRead2Controller) {
                adatbazisRead2Controller.initialize();
            }
        });
    }

    public void adatbazisCreateClick() {
        loadView("adatbazis-create.fxml", controller -> {
            if (controller instanceof com.example.project.controllers.AdatbazisCreateController createController) {
                createController.initialize();
            }
        });
    }

    public void adatbazisUpdateClick() {
        loadView("adatbazis-update.fxml", controller -> {
            if (controller instanceof com.example.project.controllers.AdatbazisUpdateController updateController) {
                updateController.initialize();
            }
        });
    }

    public void adatbazisDeleteClick() {
        loadView("adatbazis-delete.fxml", controller -> {
            if (controller instanceof com.example.project.controllers.AdatbazisDeleteController deleteController) {
               deleteController.initialize();
            }
        });
    }

    private void loadView(String fxmlPath, Consumer<Object> injector) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node content = loader.load();

            if (injector != null) {
                injector.accept(loader.getController());
            }

            placeholder.getChildren().clear();
            placeholder.getChildren().add(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
