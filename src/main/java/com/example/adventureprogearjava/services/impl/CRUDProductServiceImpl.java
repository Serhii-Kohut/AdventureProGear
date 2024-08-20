package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.exceptions.NoContentException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.ProductMapper;
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
public class CRUDProductServiceImpl implements CRUDService<ProductDTO> {
    ProductRepository productRepo;

    ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAll() {
        log.info("Getting all products");
        return productRepo.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDTO getById(Long id) {
        log.info("Getting products by id: {}", id);
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            log.warn("Product not found!");
            throw new ResourceNotFoundException("Resource is not available!");
        }
        return product.map(productMapper::toDto).get();
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        log.info("Creating new product.");

        Long generatedId = insertProduct(productDTO);

        productDTO.setProductId((long) generatedId);

        return productDTO;
    }

    @Override
    @Transactional
    public void update(ProductDTO productDTO, Long id) {
        log.info("Updating product with id: {}", id);
        if (!productRepo.existsById(id)) {
            log.warn("Product not found!");
            throw new ResourceNotFoundException("Resource is not available!");
        } else {
            if (productDTO.getGender() != null) {
                productRepo.updateGender(id, productDTO.getGender().toString());
            }
            productRepo.update(id, productDTO.getProductNameEn(),
                    productDTO.getProductNameUa(),
                    productDTO.getDescriptionEn(),
                    productDTO.getDescriptionUa(),
                    productDTO.getBasePrice(),
                    productDTO.getCategory().getId());
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting product with id: {}", id);
        if (!productRepo.existsById(id)) {
            log.warn("No content present!");
            throw new NoContentException("No content present!");
        }
        productRepo.deleteById(id);
    }

    private Long insertProduct(ProductDTO productDTO) {
        return productRepo.insertProduct(
                productDTO.getProductNameEn(),
                productDTO.getProductNameUa(),
                productDTO.getDescriptionEn(),
                productDTO.getDescriptionUa(),
                productDTO.getBasePrice(),
                productDTO.getGender().toString(),
                productDTO.getCategory().getId());

    }
}
