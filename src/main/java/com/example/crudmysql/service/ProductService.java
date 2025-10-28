package com.example.crudmysql.service;// ProductService.java
import com.example.crudmysql.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    void saveProduct(Product product);
    Optional<Product> getProductById(Integer id); // Thêm phương thức này
    void deleteProductById(Integer id); // Thêm phương thức này
    public List<Product> searchProducts(String keyword);
}