package com.foodApp.controller;

import com.foodApp.dto.request.CartItemRequest;
import com.foodApp.dto.response.CartResponse;
import com.foodApp.dto.response.ResponseData;
import com.foodApp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseData<CartResponse> getCart() {
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cart retrieved successfully")
                .data(cartService.getCart())
                .build();
    }

    @PostMapping("/items")
    public ResponseData<CartResponse> addMenuItemToCart(@RequestBody CartItemRequest request) {
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Menu item added to cart successfully")
                .data(cartService.addMenuItemToCart(request))
                .build();
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseData<CartResponse> removeCartItem(@PathVariable Long cartItemId) {
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cart item removed successfully")
                .data(cartService.removeCartItem(cartItemId))
                .build();
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseData<CartResponse> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cart item quantity updated successfully")
                .data(cartService.updateCartItemQuantity(cartItemId, quantity))
                .build();
    }

    @DeleteMapping
    public ResponseData<CartResponse> clearCart() {
        return ResponseData.<CartResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cart cleared successfully")
                .data(cartService.clearCart())
                .build();
    }
}