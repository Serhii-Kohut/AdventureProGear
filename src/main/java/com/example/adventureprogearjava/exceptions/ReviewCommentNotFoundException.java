package com.example.adventureprogearjava.exceptions;

public class ReviewCommentNotFoundException extends RuntimeException {
    public ReviewCommentNotFoundException(String message) {
        super(message);
    }
}
