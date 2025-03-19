package com.foodApp.service;

import com.foodApp.dto.request.CartItemRequest;
import com.foodApp.dto.response.CartResponse;
import com.foodApp.entity.Cart;
import jakarta.transaction.Transactional;

public interface CartService {
    @Transactional
    Cart findByUsername(String email);

    CartResponse getCart();

    CartResponse addMenuItemToCart(CartItemRequest request);

    @Transactional
    CartResponse removeCartItem(Long cartItemId);

    @Transactional
    CartResponse updateCartItemQuantity(Long cartItemId, Integer quantity);

    @Transactional
    CartResponse clearCart();
}
