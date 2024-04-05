package com.example.adventureprogearjava.exceptions;

public class UserIsNotActiveException extends RuntimeException {
    public UserIsNotActiveException(String message) {
        super(message);
    }
}
