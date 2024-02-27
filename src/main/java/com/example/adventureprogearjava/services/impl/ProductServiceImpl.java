package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.ProductMapper;
import com.example.adventureprogearjava.repositories.ProductRepository;
import com.example.adventureprogearjava.services.CRUDService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements CRUDService<ProductDTO> {
    ProductRepository productRepo;

    ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAll() {
        return productRepo.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDTO getById(Long id) throws ResourceNotFoundException {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Resource is not available!");
        }
        return product.map(productMapper::toDto).get();
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        productRepo.save(productMapper.toEntity(productDTO));
        return productDTO;
    }

    @Override
    public void update(ProductDTO productDTO, Long id) {
        //TODO Perform an update query from repository, in order to simplify code
    }

    @Override
    public void delete(Long id) {
        productRepo.deleteById(id);
    }
}
