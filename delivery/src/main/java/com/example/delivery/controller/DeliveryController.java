package com.example.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        Delivery saved = deliveryService.save(delivery);
        return ResponseEntity.ok(saved);
    }
}