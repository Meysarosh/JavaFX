package com.example.project.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiService {

    private static final String API_URL = "https://api-fxpractice.oanda.com/v3/accounts/";
    private static final String AUTH_TOKEN = "43661157882454f054ce8b541346e0e7-ad207a56f91b867d89f59a3884fcb876";
    private static final String ACCOUNT_ID = "101-004-30461860-001";

    public static PriceData fetchPricing(String currencyPair) throws Exception {
//        String url = "https://api-fxtrade.oanda.com/v3/accounts/<ACCOUNT>/pricing?instruments=EUR_USD%2CUSD_CAD";
        String url = API_URL + ACCOUNT_ID + "/pricing?instruments=" + currencyPair;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            InputStream inputStream = connection.getInputStream();
            String jsonResponse = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Pricing JSON response: " + jsonResponse);

            // Parse JSON response
            Gson gson = new Gson();
            JsonObject response = gson.fromJson(jsonResponse, JsonObject.class);
            JsonObject priceObject = response.getAsJsonArray("prices").get(0).getAsJsonObject();

            return new PriceData(
                    priceObject.get("instrument").getAsString(),
                    priceObject.get("closeoutAsk").getAsString(),
                    priceObject.get("closeoutBid").getAsString()
            );
        } else {
            throw new RuntimeException("Failed to fetch pricing data: HTTP code " + responseCode);
        }
    }


    public static AccountSummary fetchAccountSummary() throws Exception {
        String url = API_URL + ACCOUNT_ID + "/summary";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {

                InputStream inputStream = connection.getInputStream();

                String jsonResponse = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                Gson gson = new Gson();
                JsonObject response = gson.fromJson(jsonResponse, JsonObject.class);
                JsonObject account = response.getAsJsonObject("account");

                return new AccountSummary(
                        getAsStringOrNull(account, "alias"),
                        getAsStringOrNull(account, "balance"),
                        getAsStringOrNull(account, "currency"),
                        getAsStringOrNull(account, "marginRate"),
                        getAsStringOrNull(account, "pl"),
                        getAsStringOrNull(account, "nav")
                );
            } else {
                throw new RuntimeException("Failed to fetch data: HTTP code " + responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch data", e);
        }
    }

    private static String getAsStringOrNull(JsonObject account, String alias) {
            return account.has(alias) && !account.get(alias).isJsonNull() ? account.get(alias).getAsString() : "null";
        }
}


