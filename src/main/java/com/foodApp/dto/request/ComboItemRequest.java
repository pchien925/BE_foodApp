package com.foodApp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ComboItemRequest {
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

    @Min(value = 1, message = "Invalid menu item ID")
    @NotNull(message = "Menu item ID is required")
    private Long menuItemId;
}
