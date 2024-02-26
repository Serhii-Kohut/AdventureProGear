package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ProductContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductContentRepository extends JpaRepository<ProductContent, Long> {
}
