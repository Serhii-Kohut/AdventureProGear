package com.example.adventureprogearjava.exceptions;

import java.nio.file.AccessDeniedException;

public class FileAccessDeniedException extends AccessDeniedException {
    public FileAccessDeniedException(String msg) {
        super(msg);
    }
}
