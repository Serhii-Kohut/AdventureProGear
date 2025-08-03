package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.entity.ProductAttribute;
import com.example.adventureprogearjava.exceptions.NoContentException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.ProductAttributeMapper;
import com.example.adventureprogearjava.mapper.ProductMapper;
import com.example.adventureprogearjava.repositories.ProductAttributeRepository;
import com.example.adventureprogearjava.repositories.ProductRepository;
import com.example.adventureprogearjava.services.ProductAttributeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CRUDProductAttributeServiceImpl implements ProductAttributeService {
    ProductAttributeRepository productAttributeRepo;
    ProductRepository productRepository;
    ProductAttributeMapper productAttributeMapper;
    ProductMapper productMapper;

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
                productAttributeDTO.getLabel(),
                productAttributeDTO.getPictureUrl()
        );

        ProductAttribute createdProductAttribute = productAttributeRepo.findTopByOrderByIdDesc();
        return productAttributeMapper.toDto(createdProductAttribute);
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
                    productAttributeDTO.getLabel(),
                    productAttributeDTO.getPictureUrl());
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

    public Page<ProductDTO> getProductsByAttributeLabel(String label, Pageable pageable) {
        log.info("Getting products by attribute label: {} with pageable: {}", label, pageable);
        Page<Product> productsPage = productRepository.findProductsByAttributeLabel(label, pageable);

        if (productsPage.isEmpty()) {
            throw new ResourceNotFoundException("No products found for label: " + label);
        }

        List<ProductDTO> products = productsPage.getContent().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(products, pageable, productsPage.getTotalElements());
    }
}
