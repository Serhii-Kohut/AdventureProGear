package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ContentDTO;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.StorageService;
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

    @Override
    public Resource get(String filename) {
        try {
            log.info("Getting file with filename: " + filename);
            Path file = root.resolve(filename + ".txt");
            byte[] data = Files.readAllBytes(file);
            // Return ResponseEntity with image content type
            return new ByteArrayResource(data);
        } catch (IOException e) {
            log.error("Error reading the file: " + e.getMessage());
            Path file = root.resolve("file.txt");
            try {
                byte[] data = Files.readAllBytes(file);
                return new ByteArrayResource(data);
            } catch (IOException ex) {
                log.error("Fatal error. Can't retrieve default file");
                return null;
                //TODO Create an empty directory for files if not exist
                //TODO Create default file if not exist
            }
        }
    }

    @Override
    public void load(MultipartFile file, String source) {
        try {
            log.info("Loading file with filename: " + file.getName());
            try (InputStream is = file.getInputStream()) {
                byte[] stream = is.readAllBytes();
                FileOutputStream outputStream = new FileOutputStream("uploads/" + source + ".txt");
                outputStream.write(stream);
                outputStream.close();
            } catch (IOException e) {
                log.error("Error reading file: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
