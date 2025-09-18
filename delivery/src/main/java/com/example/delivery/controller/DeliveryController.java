package com.example.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.delivery.model.Delivery;
import com.example.delivery.service.DeliveryService;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    
    @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        Delivery saved = deliveryService.save(delivery);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{invoiceNumber}") 
    public ResponseEntity<?> getDelivery(@PathVariable String invoiceNumber) {
        Delivery delivery = deliveryService.findByInvoiceNumber(invoiceNumber).orElse(null);
        if (delivery == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(delivery);
    }
}