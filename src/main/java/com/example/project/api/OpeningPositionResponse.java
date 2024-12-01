package com.example.project.api;

import com.google.gson.annotations.SerializedName;

public class OpeningPositionResponse {
    @SerializedName("orderCancelTransaction")
    private Transaction orderCancelTransaction;
    @SerializedName("orderFillTransaction")
    private Transaction orderFillTransaction;

    public static class Transaction {
        @SerializedName("reason")
        private String reason;

        public String getReason() {
            return reason;
        }
    }

    public Transaction getOrderCancelTransaction() {
        return orderCancelTransaction;
    }

    public void setOrderCancelTransaction(Transaction orderCancelTransaction) {
        this.orderCancelTransaction = orderCancelTransaction;
    }

    public Transaction getOrderFillTransaction() {
        return orderFillTransaction;
    }

    public void setOrderFillTransaction(Transaction orderFillTransaction) {
        this.orderFillTransaction = orderFillTransaction;
    }
}


