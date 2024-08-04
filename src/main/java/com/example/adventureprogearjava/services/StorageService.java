package com.example.adventureprogearjava.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    Resource get(String filename);

    void load(MultipartFile file, String source);
}
