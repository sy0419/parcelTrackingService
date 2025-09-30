package com.example.delivery.exception;

// 배송 상태 변경이 유효하지 않을 때 발생하는 예외 클래스
// # Custom exception thrown when an invalid status transition is attempted
public class InvalidStatusChangeException extends RuntimeException {

    // 예외 메시지를 받아서 상위 RuntimeException에 전달
    // # Passes the error message to the parent RuntimeException
    public InvalidStatusChangeException(String message) {
        super(message);
    }
}