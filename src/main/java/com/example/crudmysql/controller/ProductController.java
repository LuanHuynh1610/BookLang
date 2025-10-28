package com.example.crudmysql.controller;

import com.example.crudmysql.model.Product;
import com.example.crudmysql.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Hiển thị danh sách sản phẩm với chức năng tìm kiếm
    @GetMapping
    public String listProducts(@RequestParam(value = "keyword", required = false) String keyword,
                               Model model) {
        List<Product> products;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Tìm kiếm sản phẩm theo từ khóa
            products = productService.searchProducts(keyword.trim());
            model.addAttribute("keyword", keyword.trim());
        } else {
            // Hiển thị tất cả sản phẩm
            products = productService.getAllProducts();
        }

        model.addAttribute("products", products);
        return "product_list";
    }

    @GetMapping("/new")
    public String showNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "new_product";
    }

    // Lưu sản phẩm mới hoặc cập nhật
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Integer id, Model model) {
        Optional<Product> p = productService.getProductById(id);
        if (p.isPresent()) {
            model.addAttribute("product", p.get());
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm
            // Chuyển hướng về trang danh sách hoặc hiển thị thông báo lỗi
            return "redirect:/products";
        }

        return "new_product";
    }

    // Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    // Thêm chức năng xem chi tiết sản phẩm
    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model) {
        Optional<Product> optionalProduct = productService.getProductById(id);

        if (optionalProduct.isPresent()) {
            model.addAttribute("product", optionalProduct.get());
            return "product_detail"; // Trả về template chi tiết sản phẩm
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm
            return "redirect:/products";
        }
    }
}
