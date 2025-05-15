package com.tuannghia.andshop.service;

import com.tuannghia.andshop.dto.CartDTO;
import com.tuannghia.andshop.dto.CartItemDTO;
import jakarta.servlet.http.HttpSession;

public interface CartService {
    void addToCart(HttpSession session, CartItemDTO cartItem);

    void updateCartItemQuantity(HttpSession session, Long productId, int quantity);

    void removeCartItem(HttpSession session, Long productId);

    void clearCart(HttpSession session);

    CartDTO getCart(HttpSession session);
}

