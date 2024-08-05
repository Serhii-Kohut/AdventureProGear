package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.exceptions.FileAccessDeniedException;
import com.example.adventureprogearjava.services.StorageService;
import org.springframework.core.io.Resource;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {
    private final StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Resource> getFile(@PathVariable String name) {
        Resource resource = storageService.get(name);
    public ResponseEntity<Resource> getFile(@PathVariable String name) throws FileAccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new FileAccessDeniedException("Only admins are allowed to download files.");
        }
        Resource resource = (Resource) storageService.get(name);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @PostMapping("/{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String name) {
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String name) throws FileAccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new FileAccessDeniedException("Only admins are allowed to upload files.");
        }
        try {
            storageService.load(file, name);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File upload failed: " + e.getMessage());
        }
    }
}
