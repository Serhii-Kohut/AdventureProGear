package com.example.adventureprogearjava.exceptions;

public class NoContentException extends RuntimeException{
    public NoContentException() {
    }

    public NoContentException(String message) {
        super(message);
    }

    public NoContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoContentException(Throwable cause) {
        super(cause);
    }
}
