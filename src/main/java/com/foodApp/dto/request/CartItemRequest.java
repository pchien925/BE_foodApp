package com.foodApp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Set;

@Getter
public class CartItemRequest {
    @NotNull(message = "Menu item ID is required")
    @Min(value = 1, message = "Invalid menu item ID")
    private Long menuItemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Invalid quantity")
    private Integer quantity;

    private String note;

    private Set<Long> selectedOptions;
}
