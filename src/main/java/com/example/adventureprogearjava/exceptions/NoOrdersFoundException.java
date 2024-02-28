package com.example.adventureprogearjava.exceptions;

public class NoOrdersFoundException extends RuntimeException{
    public NoOrdersFoundException(String message) {
        super(message);
    }
}
