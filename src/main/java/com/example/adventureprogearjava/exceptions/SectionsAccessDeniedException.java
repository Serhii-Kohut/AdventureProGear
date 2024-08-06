package com.example.adventureprogearjava.exceptions;

import org.springframework.security.access.AccessDeniedException;

public class SectionsAccessDeniedException extends AccessDeniedException {
    public SectionsAccessDeniedException(String msg) {
        super(msg);
    }
}
