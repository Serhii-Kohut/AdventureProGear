package com.example.adventureprogearjava.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoOrdersFoundException extends RuntimeException{
    public NoOrdersFoundException(String message) {
        super(message);
    }
}
