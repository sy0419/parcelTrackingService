package com.example.delivery.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    /**
     * 새로운 배송 생성 API
     * Create a new delivery
     * 
     * @param delivery 배송 정보 # Delivery info
     * @return 저장된 배송 객체 # Saved delivery object
     */
    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        Delivery saved = deliveryService.save(delivery);
        return ResponseEntity.ok(saved);
    }

    /**
     * 송장번호로 배송 조회 API
     * Get delivery info by invoice number
     * 
     * @param invoiceNumber 송장번호 # Invoice number
     * @return 배송 객체 혹은 404 상태 # Delivery object or 404 if not found
     */
    @GetMapping("/{invoiceNumber}") 
    public ResponseEntity<?> getDelivery(@PathVariable String invoiceNumber) {
        Delivery delivery = deliveryService.findByInvoiceNumber(invoiceNumber);
        if (delivery == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(delivery);
    }

    /**
     * 배송 상태 변경 API (관리자용)
     * Update delivery status (Admin only)
     * 
     * @param invoiceNumber 송장번호 # Invoice number
     * @param statusRequest 상태 변경 요청 JSON, newStatus 키 포함 # JSON body containing "newStatus"
     * @return 상태 변경된 배송 객체 # Delivery object with updated status
     */
    @PutMapping("/{invoiceNumber}/status")
    public ResponseEntity<Delivery> updateStatus(@PathVariable String invoiceNumber, @RequestBody Map<String, String> statusRequest) {
        String newStatus = statusRequest.get("newStatus");
        Delivery updatedDelivery = deliveryService.changeStatus(invoiceNumber, newStatus);
        return ResponseEntity.ok(updatedDelivery);
    }
}