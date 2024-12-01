package com.example.project.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ParhuzamosController {

    @FXML private Label label1;
    @FXML private Label label2;
    private ScheduledExecutorService scheduler;

    @FXML public void startParallelTasks() {
        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newScheduledThreadPool(2);

            scheduler.scheduleAtFixedRate(() -> Platform.runLater(() ->
                    label1.setText("Label 1: " + System.currentTimeMillis())
            ), 0, 1, TimeUnit.SECONDS);

            scheduler.scheduleAtFixedRate(() -> Platform.runLater(() ->
                    label2.setText("Label 2: " + System.currentTimeMillis())
            ), 0, 2, TimeUnit.SECONDS);
        }
    }

    @FXML public void shutdown() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
