package com.example.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.delivery.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {
    // invoiceNumber가 String이므로 기본 키 타입은 String
}