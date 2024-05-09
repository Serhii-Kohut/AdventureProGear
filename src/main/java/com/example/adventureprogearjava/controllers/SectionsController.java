package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.SectionDTO;
import com.example.adventureprogearjava.services.CRUDService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    public SectionDTO createSection(@RequestBody SectionDTO sectionDTO){
        return crudService.create(sectionDTO);
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
