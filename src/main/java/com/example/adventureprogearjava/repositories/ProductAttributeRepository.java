package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
}
