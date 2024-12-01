package com.example.project.api;

import com.google.gson.annotations.SerializedName;

public class ClosePositionResponse {

    @SerializedName("longOrderFillTransaction")
    private Transaction longOrderFillTransaction;

    @SerializedName("shortOrderFillTransaction")
    private Transaction shortOrderFillTransaction;

    @SerializedName("longOrderCancelTransaction")
    private Transaction longOrderCancelTransaction;

    @SerializedName("shortOrderCancelTransaction")
    private Transaction shortOrderCancelTransaction;

    public Transaction getLongOrderFillTransaction() {
        return longOrderFillTransaction;
    }

    public Transaction getShortOrderFillTransaction() {
        return shortOrderFillTransaction;
    }

    public Transaction getLongOrderCancelTransaction() {
        return longOrderCancelTransaction;
    }

    public Transaction getShortOrderCancelTransaction() {
        return shortOrderCancelTransaction;
    }

    public static class Transaction {
        @SerializedName("reason")
        private String reason;

        public String getReason() {
            return reason;
        }
    }
}
