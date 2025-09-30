package com.example.delivery.exception;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException(String invoiceNumber) {
        super("Delivery not found for invoice number: " + invoiceNumber);
    }
}