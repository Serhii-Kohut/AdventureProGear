package com.example.adventureprogearjava.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoOrdersListFoundException extends RuntimeException {
    public NoOrdersListFoundException(String message) {
        super(message);
    }
}
