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

    /**
     * 배송 정보 저장
     * Save delivery information
     * 
     * @param delivery 배송 객체 # Delivery object
     * @return 저장된 배송 객체 # Saved delivery object
     */
    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    /**
     * 송장번호로 배송 조회
     * Find delivery by invoice number
     * 
     * @param invoiceNumber 송장번호 # Invoice number
     * @return 배송 객체 # Delivery object
     * @throws DeliveryNotFoundException 해당 송장이 없을 때 예외 발생 # Throws exception if invoice not found
     */
    public Delivery findByInvoiceNumber(String invoiceNumber) {
        return deliveryRepository.findById(invoiceNumber)
            .orElseThrow(() -> new DeliveryNotFoundException(invoiceNumber));
    }

    /**
     * 배송 상태 변경 처리
     * Change delivery status
     * 
     * @param invoiceNumber 송장번호 # Invoice number
     * @param newStatus 변경 요청 상태 문자열 # New requested status string
     * @return 상태 변경 후 배송 객체 # Delivery object after status update
     * @throws InvalidStatusChangeException 유효하지 않은 상태 변경 시 예외 발생 # Throws exception on invalid status change
     */
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