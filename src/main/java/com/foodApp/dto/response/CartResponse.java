package com.foodApp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class CartResponse {
    private Long id;
    private Long userId;
    private Set<CartItemResponse> cartItems;
}
