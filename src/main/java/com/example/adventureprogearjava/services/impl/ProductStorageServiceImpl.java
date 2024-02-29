package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductStorageDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.entity.ProductStorage;
import com.example.adventureprogearjava.exceptions.NoContentException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.ProductStorageMapper;
import com.example.adventureprogearjava.repositories.ProductStorageRepository;
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
public class ProductStorageServiceImpl implements CRUDService<ProductStorageDTO> {
    ProductStorageRepository storageRepository;

    ProductStorageMapper storageMapper;

    @Override
    public List<ProductStorageDTO> getAll() {
        log.info("Getting all products from storage");
        return storageRepository.findAll()
                .stream()
                .map(storageMapper::toDto)
                .toList();
    }

    @Override
    public ProductStorageDTO getById(Long id) {
        log.info("Getting products from storage by id: {}", id);
        Optional<ProductStorage> productStorage = storageRepository.findById(id);
        if (productStorage.isEmpty()) {
            log.warn("Product not found!");
            throw new ResourceNotFoundException("Resource is not available!");
        }
        return productStorage.map(storageMapper::toDto).get();
    }

    @Override
    public ProductStorageDTO create(ProductStorageDTO productStorageDTO) {
        log.info("Creating new storage.");
        ProductStorage savedStorage = storageRepository
                .save(storageMapper.toEntity(productStorageDTO));
        return storageMapper.toDto(savedStorage);
    }

    @Override
    public void update(ProductStorageDTO productStorageDTO, Long id) {
        log.info("Updating storage with id: {}", id);
        if (!storageRepository.existsById(id)) {
            log.warn("Storage not found!");
            throw new ResourceNotFoundException("Resource is not available!");
        } else {
            ProductStorage toUpdate = storageRepository.findById(id).get();
            toUpdate.setQuantity(productStorageDTO.getQuantity().longValue());
            storageRepository.save(toUpdate);
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting product storage with id: {}", id);
        if (!storageRepository.existsById(id)) {
            log.warn("No content present!");
            throw new NoContentException("No content present!");
        }
        storageRepository.deleteById(id);
    }
}
