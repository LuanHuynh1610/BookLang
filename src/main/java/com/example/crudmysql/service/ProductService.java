package com.example.crudmysql.service;

import com.example.crudmysql.model.Product;
import java.util.List;
import java.util.Optional;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    void saveProduct(Product product);
    Optional<Product> getProductById(Integer id); // Thêm phương thức này
    void deleteProductById(Integer id); // Thêm phương thức này
}