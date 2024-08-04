package com.example.adventureprogearjava.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ContentService {
    Resource getResourceFromSource(String source);

    void saveContent(MultipartFile file);
}
