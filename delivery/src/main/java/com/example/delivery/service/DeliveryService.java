package com.example.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.delivery.exception.DeliveryNotFoundException;
import com.example.delivery.exception.InvalidStatusChangeException;
import com.example.delivery.model.Delivery;
import com.example.delivery.model.DeliveryStatus;
import com.example.delivery.repository.DeliveryRepository;

@Service
public class DeliveryService {
    
    @Autowired
    private DeliveryRepository deliveryRepository;

    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Delivery findByInvoiceNumber(String invoiceNumber) {
        return deliveryRepository.findById(invoiceNumber)
            .orElseThrow(() -> new DeliveryNotFoundException(invoiceNumber));
    }

    public Delivery changeStatus(String invoiceNumber, String newStatus) {
        Delivery delivery = findByInvoiceNumber(invoiceNumber);
        DeliveryStatus requestedStatus = DeliveryStatus.valueOf(newStatus.toUpperCase());
        DeliveryStatus currentStatus = delivery.getStatus();
        if (!currentStatus.canMoveTo(requestedStatus)) {
            throw new InvalidStatusChangeException("Cannot change status from " 
            + currentStatus + " to " + requestedStatus);
        }
        delivery.setStatus(requestedStatus);
        return deliveryRepository.save(delivery);
    }
}