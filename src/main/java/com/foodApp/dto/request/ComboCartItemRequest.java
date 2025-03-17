package com.foodApp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ComboCartItemRequest {

    @NotNull(message = "Combo ID is required")
    @Min(value = 1, message = "Invalid combo ID")
    private Long comboId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Invalid quantity")
    private Integer quantity;

    private String note;
}
