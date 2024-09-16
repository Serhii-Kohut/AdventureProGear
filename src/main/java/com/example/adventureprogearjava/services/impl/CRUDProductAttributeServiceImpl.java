package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.entity.ProductAttribute;
import com.example.adventureprogearjava.exceptions.NoContentException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.ProductAttributeMapper;
import com.example.adventureprogearjava.repositories.ProductAttributeRepository;
import com.example.adventureprogearjava.repositories.ProductRepository;
import com.example.adventureprogearjava.services.CRUDService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CRUDProductAttributeServiceImpl implements CRUDService<ProductAttributeDTO> {
    ProductAttributeRepository productAttributeRepo;
    ProductRepository productRepository;
    ProductAttributeMapper productAttributeMapper;

    @Override
    public List<ProductAttributeDTO> getAll() {
        log.info("Getting all productAttributes");
        return productAttributeRepo.findAll()
                .stream()
                .map(productAttributeMapper::toDto)
                .toList();
    }

    @Override
    public ProductAttributeDTO getById(Long id) {
        log.info("Getting productAttributes by id: {}", id);
        Optional<ProductAttribute> productAttribute = productAttributeRepo.findById(id);
        if (productAttribute.isEmpty()) {
            throw new ResourceNotFoundException("Resource is not available!");
        }
        return productAttribute.map(productAttributeMapper::toDto).get();
    }

    @Override
    @Transactional
    public ProductAttributeDTO create(ProductAttributeDTO productAttributeDTO) {
        log.info("Creating new productAttribute.");
        if (!productRepository.existsById(productAttributeDTO.getProductId())) {
            throw new ResourceNotFoundException("Product with ID " + productAttributeDTO.getProductId() + " not found.");
        }
        productAttributeRepo.insertProductAttr(productAttributeDTO.getSize(),
                productAttributeDTO.getColor(),
                productAttributeDTO.getAdditional(),
                productAttributeDTO.getPriceDeviation(),
                productAttributeDTO.getProductId(),
                productAttributeDTO.getQuantity(),
                productAttributeDTO.getLabel());
        return productAttributeDTO;
    }

    @Override
    @Transactional
    public void update(ProductAttributeDTO productAttributeDTO, Long id) {
        log.info("Updating productAttribute with id: {}", id);
        if (!productAttributeRepo.existsById(id)) {
            log.warn("ProductAttribute not found!");
            throw new ResourceNotFoundException("Resource is not available!");
        } else {
            productAttributeRepo.update(id,
                    productAttributeDTO.getSize(),
                    productAttributeDTO.getAdditional(),
                    productAttributeDTO.getColor(),
                    productAttributeDTO.getPriceDeviation(),
                    productAttributeDTO.getQuantity(),
                    productAttributeDTO.getLabel());
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting productAttribute with id: {}", id);
        if (!productAttributeRepo.existsById(id)) {
            log.warn("No content present!");
            throw new NoContentException("No content present!");
        }
        productAttributeRepo.deleteById(id);
    }
}
