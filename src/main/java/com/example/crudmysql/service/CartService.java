package com.example.crudmysql.service;

import com.example.crudmysql.model.CartItem;
import com.example.crudmysql.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "shoppingCart";

    // Lấy giỏ hàng từ session. Nếu chưa có, tạo mới.
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(HttpSession session, Product product, int quantity) {
        List<CartItem> cart = getCart(session);
        Optional<CartItem> existingItem = cart.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Cập nhật số lượng nếu sản phẩm đã có
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            // Thêm mới
            cart.add(new CartItem(product, quantity));
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(HttpSession session, Integer productId) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    // Lấy tất cả các mặt hàng trong giỏ
    public List<CartItem> getCartItems(HttpSession session) {
        return getCart(session);
    }

    // Tính tổng tiền giỏ hàng
    public float getCartTotal(HttpSession session) {
        return (float) getCart(session).stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    // Xóa toàn bộ giỏ hàng (cho chức năng thanh toán)
    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
}
