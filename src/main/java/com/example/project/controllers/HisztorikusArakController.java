package com.example.project.controllers;

import com.example.project.api.ApiService;
import com.example.project.api.HistoricalPriceData;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HisztorikusArakController {

    @FXML private ComboBox<String> currencyDropdown;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private LineChart<String, Number> priceChart;
    @FXML private NumberAxis priceAxis;
    @FXML private CategoryAxis dateAxis;
    @FXML private Pane parentPane;

    private static final String[] CURRENCIES = {"EUR_USD", "USD_JPY", "GBP_USD", "USD_CHF"};

    @FXML
    public void initialize() {
        currencyDropdown.getItems().addAll(CURRENCIES);
        currencyDropdown.getSelectionModel().selectFirst(); // Default selection
    }

    @FXML
    public void fetchHistoricalData() {
        String currency = currencyDropdown.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (currency == null || startDate == null || endDate == null) {
            System.out.println("Please select a currency and date range!");
            return;
        }

        try {
            String formattedStartDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "T00:00:00.000000000Z";
            long daysBetween = endDate.toEpochDay() - startDate.toEpochDay() + 1;

            List<HistoricalPriceData> prices = ApiService.fetchHistoricalPrices(currency, formattedStartDate, (int) daysBetween);

            updateChart(prices, currency);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateChart(List<HistoricalPriceData> prices, String currency) {
        CategoryAxis newDateAxis = new CategoryAxis();
        newDateAxis.setLabel("Date");
        newDateAxis.setTickLabelRotation(45);

        LineChart<String, Number> newPriceChart = new LineChart<>(newDateAxis, priceAxis);
        newPriceChart.setTitle("Price Chart");
        newPriceChart.setLegendVisible(true);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(currency);

        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;

        for (HistoricalPriceData data : prices) {
            String truncatedTime = data.time().split("T")[0];
            double currentPrice = Double.parseDouble(data.askClose());
            series.getData().add(new XYChart.Data<>(truncatedTime, currentPrice));

            if (currentPrice < minPrice) {
                minPrice = currentPrice;
            }
            if (currentPrice > maxPrice) {
                maxPrice = currentPrice;
            }

            if (!newDateAxis.getCategories().contains(truncatedTime)) {
                newDateAxis.getCategories().add(truncatedTime);
            }
        }

        double range = maxPrice - minPrice;
        double tickUnit = range / 5;

        double buffer = range * 0.05;
        minPrice -= buffer;
        maxPrice += buffer;

        priceAxis.setAutoRanging(false);
        priceAxis.setLowerBound(minPrice);
        priceAxis.setUpperBound(maxPrice);
        priceAxis.setTickUnit(tickUnit);

        newPriceChart.getData().add(series);

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newPriceChart);

        priceChart = newPriceChart;
    }

}
