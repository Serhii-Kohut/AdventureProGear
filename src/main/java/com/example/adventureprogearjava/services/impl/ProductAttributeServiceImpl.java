package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.entity.ProductAttribute;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.ProductAttributeMapper;
import com.example.adventureprogearjava.repositories.ProductAttributeRepository;
import com.example.adventureprogearjava.services.CRUDService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeServiceImpl implements CRUDService<ProductAttributeDTO> {
    ProductAttributeRepository productAttributeRepo;

    ProductAttributeMapper productAttributeMapper;

    @Override
    public List<ProductAttributeDTO> getAll() {
        return productAttributeRepo.findAll()
                .stream()
                .map(productAttributeMapper::toDto)
                .toList();
    }

    @Override
    public ProductAttributeDTO getById(Long id) {
        Optional<ProductAttribute> productAttribute = productAttributeRepo.findById(id);
        if (productAttribute.isEmpty()) {
            throw new ResourceNotFoundException("Resource is not available!");
        }
        return productAttribute.map(productAttributeMapper::toDto).get();
    }

    @Override
    public ProductAttributeDTO create(ProductAttributeDTO productAttributeDTO) {
        productAttributeRepo.save(productAttributeMapper
                .toEntity(productAttributeDTO));
        return productAttributeDTO;
    }

    @Override
    @Transactional
    public void update(ProductAttributeDTO productAttributeDTO, Long id) {
        if (!productAttributeRepo.existsById(id)) {
            productAttributeRepo.save(productAttributeMapper
                    .toEntity(productAttributeDTO));
        } else {
            productAttributeRepo.update(id,
                    productAttributeDTO.getSize(),
                    productAttributeDTO.getAdditional(),
                    productAttributeDTO.getColor(),
                    productAttributeDTO.getPriceDeviation());
        }
    }

    @Override
    public void delete(Long id) {
        productAttributeRepo.deleteById(id);
    }
}
