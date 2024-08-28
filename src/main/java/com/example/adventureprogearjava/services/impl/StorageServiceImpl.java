package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ContentDTO;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StorageServiceImpl implements StorageService {
    Path root = Paths.get("uploads");

    CRUDService<ContentDTO> crudService;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            log.error("Could not initialize storage directory: " + e.getMessage());
            throw new RuntimeException("Could not initialize storage directory", e);
        }
    }

    @Override
    public Resource get(String filename) {
        try {
            log.info("Getting file with filename: " + filename);
            Path file = root.resolve(filename);
            byte[] data = Files.readAllBytes(file);
            return new ByteArrayResource(data);
        } catch (IOException e) {
            log.error("Error reading the file: " + e.getMessage());
            throw new RuntimeException("File not found: " + filename, e);
        }
    }

    @Override
    public void load(MultipartFile file, String source) {
        try {
            log.info("Loading file with original filename: " + file.getOriginalFilename());
            Path destinationFile = root.resolve(source);
            try (FileOutputStream outputStream = new FileOutputStream(destinationFile.toFile());
                 InputStream is = file.getInputStream()) {
                byte[] stream = is.readAllBytes();
                outputStream.write(stream);
            }
        } catch (IOException e) {
            log.error("Error saving file: " + e.getMessage());
            throw new RuntimeException("Failed to store file: " + source, e);
        }
    }
}
