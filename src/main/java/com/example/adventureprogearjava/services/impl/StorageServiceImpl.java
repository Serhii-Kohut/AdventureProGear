package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ContentDTO;
import com.example.adventureprogearjava.repositories.ProductContentRepository;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.StorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StorageServiceImpl implements StorageService {
    Path root = Paths.get("uploads");

    CRUDService<ContentDTO> crudService;

    @Override
    public Resource get(String filename) {
        try {
            Path file = root.resolve(filename + ".txt");
            byte[] data = Files.readAllBytes(file);
            // Return ResponseEntity with image content type
            return new ByteArrayResource(data);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file: " + e.getMessage());
        }

    }

    @Override
    public void load(MultipartFile file, String source) {
        try {
            try (InputStream is = file.getInputStream()) {
                byte[] stream = is.readAllBytes();
                System.out.println(file.getName());
                FileOutputStream outputStream = new FileOutputStream("uploads/" + source + ".txt");
                outputStream.write(stream);
                outputStream.close();

            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
