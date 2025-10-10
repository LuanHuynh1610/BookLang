package com.example.crudmysql.repository;

import com.example.crudmysql.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Spring Data JPA provides standard CRUD methods automatically
}
