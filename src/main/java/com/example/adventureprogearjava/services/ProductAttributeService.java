package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductAttributeService {
    List<ProductAttributeDTO> getAll();

    ProductAttributeDTO getById(Long id);

    ProductAttributeDTO create(ProductAttributeDTO productAttributeDTO);

    void update(ProductAttributeDTO productAttributeDTO, Long id);

    void delete(Long id);

    Page<ProductDTO> getProductsByAttributeLabel(String label, Pageable pageable);

}
