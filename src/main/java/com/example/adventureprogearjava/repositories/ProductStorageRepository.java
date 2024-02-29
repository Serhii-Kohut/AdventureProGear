package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.ProductStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStorageRepository extends JpaRepository<ProductStorage, Long> {

}
