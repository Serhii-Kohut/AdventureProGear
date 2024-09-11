package com.example.adventureprogearjava.exceptions;

public class AccessToOrderDeniedException extends RuntimeException {
    public AccessToOrderDeniedException(String message) {
        super(message);
    }
}
