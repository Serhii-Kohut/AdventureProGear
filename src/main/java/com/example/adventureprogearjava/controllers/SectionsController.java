package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.SectionDTO;
import com.example.adventureprogearjava.exceptions.SectionsAccessDeniedException;
import com.example.adventureprogearjava.services.CRUDService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/sections")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Sections Controller",
        description = "API operations with sections")
public class SectionsController {
    CRUDService<SectionDTO> crudService;

    @GetMapping("")
    public List<SectionDTO> getAllSections(){
        return crudService.getAll();
    }

    @GetMapping("/{id}")
    public SectionDTO getSectionById(@PathVariable("id") Long id){
        return crudService.getById(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> createSection(@RequestBody SectionDTO sectionDTO) throws SectionsAccessDeniedException {
    public ResponseEntity<String> createSection(@RequestBody SectionDTO sectionDTO) throws SectionsAccessDeniedException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new SectionsAccessDeniedException("Only admins are allowed to create sections.");
        }
        try {
            crudService.create(sectionDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Section created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void updateSection(@PathVariable("id") Long id,
                              @RequestBody SectionDTO sectionDTO){
        crudService.update(sectionDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteSection(@PathVariable("id") Long id){
        crudService.delete(id);
    }

}
