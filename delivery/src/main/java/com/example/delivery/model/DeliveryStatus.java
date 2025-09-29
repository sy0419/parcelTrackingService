package com.example.delivery.model;

public enum DeliveryStatus {
    PREPARING,   // 상품 준비 중 # Preparing the product
    SHIPPED,     // 배송 시작됨 # Shipped
    IN_TRANSIT,  // 배송 중 # In transit
    DELIVERED;   // 배송 완료 # Delivered

    /**
     * 현재 상태에서 다음 상태로 이동 가능한지 확인하는 메서드
     * Checks if transition from current status to the given next status is allowed.
     *
     * @param nextStatus 이동하려는 다음 상태 # The next status to move to
     * @return boolean 이동 가능하면 true, 불가능하면 false # true if transition allowed, else false
     */
    public boolean canMoveTo(DeliveryStatus nextStatus){
        if (null != this) switch (this) {
            case PREPARING -> {
                return nextStatus == SHIPPED;
            }
            case SHIPPED -> {
                return nextStatus == IN_TRANSIT;
            }
            case IN_TRANSIT -> {
                return nextStatus == DELIVERED;
            }
            case DELIVERED -> {
                return false;
            }
            default -> {
            }
        }
        return false;
    }
}