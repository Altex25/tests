package com.example.model;

public class OrderConfirmation {

    private final String orderId;
    private final String message;

    public OrderConfirmation(String orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getMessage() {
        return message;
    }
}
