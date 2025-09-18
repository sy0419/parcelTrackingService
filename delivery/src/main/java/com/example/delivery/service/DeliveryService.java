package com.example.delivery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.delivery.model.Delivery;
import com.example.delivery.repository.DeliveryRepository;

@Service
public class DeliveryService {
    
    @Autowired
    private DeliveryRepository deliveryRepository;

    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Optional<Delivery> findByInvoiceNumber(String invoiceNumber) {
        return deliveryRepository.findById(invoiceNumber);
    }
}