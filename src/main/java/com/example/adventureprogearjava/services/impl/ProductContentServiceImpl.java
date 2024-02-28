package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ContentDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.entity.ProductContent;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.NoContentException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.ContentMapper;
import com.example.adventureprogearjava.repositories.ProductContentRepository;
import com.example.adventureprogearjava.services.CRUDService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductContentServiceImpl implements CRUDService<ContentDTO> {
    ContentMapper contentMapper;

    ProductContentRepository contentRepository;

    @Override
    public List<ContentDTO> getAll() {
        log.info("Getting all contents");
        return contentRepository.findAll()
                .stream()
                .map(contentMapper::toDto)
                .toList();
    }

    @Override
    public ContentDTO getById(Long id) {
        log.info("Getting content by id: {}", id);
        Optional<ProductContent> content = contentRepository.findById(id);
        if (content.isEmpty()) {
            log.warn("Content not found!");
            throw new ResourceNotFoundException("Resource is not available!");
        }
        return content.map(contentMapper::toDto).get();
    }

    @Override
    public ContentDTO create(ContentDTO contentDTO) {
        log.info("Adding new content.");
        contentRepository.insertContent(contentDTO.getSource(),
                contentDTO.getProductId());
        return contentDTO;
    }

    @Override
    public void update(ContentDTO contentDTO, Long id) {
        log.info("Updating content with id: {}", id);
        ProductContent existingContent = contentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Content not found with id: {}", id);
                    return new ResourceNotFoundException("Content not found with id " + id);
                });
        existingContent.setSource(contentDTO.getSource());
        contentRepository.save(existingContent);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting content with id: {}", id);
        if (!contentRepository.existsById(id)) {
            log.warn("No content present!");
            throw new NoContentException("No content present!");
        }
        contentRepository.deleteById(id);
    }
}
