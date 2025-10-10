package com.example.crudmysql.controller;

import com.example.crudmysql.model.Product;
import com.example.crudmysql.repository.ProductRepository;
import com.example.crudmysql.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ShopController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    // --- Trang Danh sách Sản phẩm ---
    @GetMapping("/")
    public String viewHomePage(Model model) {
        // Lấy danh sách sản phẩm từ DB
        model.addAttribute("listProducts", productRepository.findAll());
        return "index"; // Trả về file index.html
    }

    // --- Thêm sản phẩm vào giỏ hàng ---
    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable(name = "id") Integer productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes ra) {

        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isPresent()) {
            cartService.addToCart(session, productOpt.get(), quantity);
            ra.addFlashAttribute("message", "Đã thêm " + productOpt.get().getName() + " vào giỏ hàng!");
        } else {
            ra.addFlashAttribute("error", "Không tìm thấy sản phẩm!");
        }

        return "redirect:/"; // Quay lại trang danh sách sản phẩm
    }

    // --- Trang Giỏ hàng ---
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute("cartItems", cartService.getCartItems(session));
        model.addAttribute("cartTotal", cartService.getCartTotal(session));
        return "cart"; // Trả về file cart.html
    }

    // --- Xóa sản phẩm khỏi giỏ hàng ---
    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable(name = "id") Integer productId,
                                 HttpSession session,
                                 RedirectAttributes ra) {

        cartService.removeFromCart(session, productId);
        ra.addFlashAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng.");
        return "redirect:/cart";
    }

    // --- Thanh toán ---
    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes ra) {
        float total = cartService.getCartTotal(session);
        if (total > 0) {
            // Giả lập logic thanh toán
            // ... (Ví dụ: Lưu Order vào DB, gọi API thanh toán)
            cartService.clearCart(session); // Xóa giỏ hàng sau khi thanh toán thành công

            ra.addFlashAttribute("message", "Thanh toán thành công! Tổng tiền: " + total + " VND.");
            return "redirect:/"; // Chuyển hướng về trang chủ
        }

        ra.addFlashAttribute("error", "Giỏ hàng trống. Không thể thanh toán.");
        return "redirect:/cart";
    }
}
