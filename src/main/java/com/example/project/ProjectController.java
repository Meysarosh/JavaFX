package com.example.project;

import com.example.project.api.AccountSummary;
import com.example.project.api.ApiService;
import com.example.project.controllers.AktualisArakController;
import com.example.project.controllers.NyitottPoziciokController;
import com.example.project.controllers.PozicioNyitasController;
import com.example.project.controllers.SzamlainformaciokController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.function.Consumer;

public class ProjectController {
    @FXML private Pane placeholder;

    public void nyitottPoziciokClick() {
        loadView("nyitott-poziciok.fxml", controller -> {
            if (controller instanceof NyitottPoziciokController nyitottPoziciokController) {
                nyitottPoziciokController.loadPositions();
            }
        });
    }

    public void pozicioNyitasClick() {
    loadView("pozicio-nyitas.fxml", controller -> {
        if (controller instanceof PozicioNyitasController pozicioNyitasController) {
            pozicioNyitasController.initializeDropdown();
        }
    });
}

    public void aktualisArakClick() {
        loadView("aktualis-arak.fxml", controller -> {
            if (controller instanceof AktualisArakController aktualisArakController) {
                aktualisArakController.initializeDropdown();
            }
        });
    }

    public void szamlainformaciokClick() {
        loadView("szamlainformaciok.fxml", controller -> {
            if (controller instanceof SzamlainformaciokController szamlainformaciokController) {
                try {
                    szamlainformaciokController.initialize();
                    AccountSummary summary = ApiService.fetchAccountSummary();
                    szamlainformaciokController.populateAccountSummary(summary);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

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
