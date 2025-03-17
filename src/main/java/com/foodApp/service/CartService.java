package com.foodApp.service;

import com.foodApp.dto.request.CartItemRequest;
import com.foodApp.dto.response.CartResponse;
import jakarta.transaction.Transactional;

public interface CartService {
    CartResponse getCart();

    CartResponse addMenuItemToCart(CartItemRequest request);

    @Transactional
    CartResponse removeCartItem(Long cartItemId);

    @Transactional
    CartResponse updateCartItemQuantity(Long cartItemId, Integer quantity);

    @Transactional
    CartResponse clearCart();
}
