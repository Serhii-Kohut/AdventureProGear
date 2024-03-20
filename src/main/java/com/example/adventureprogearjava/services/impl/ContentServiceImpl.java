package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.services.ContentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ContentServiceImpl implements ContentService {
    @Override
    public Resource getResourceFromSource(String source) {
        byte[] data = source.getBytes();
        return new ByteArrayResource(data);
    }

    @Override
    public void saveContent(MultipartFile file) {
        //TODO: Implement Saving content
    }
}
