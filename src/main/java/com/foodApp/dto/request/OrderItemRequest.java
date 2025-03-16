package com.foodApp.dto.request;

import com.foodApp.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderItemRequest {
    @NotNull(message = "Quantity must be greater than 0")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

    private Double priceAtOrder;

    private String note;

    @Min(value = 1, message = "Invalid menu item ID")
    private Long menuItemId;

    @Min(value = 1, message = "Invalid combo ID")
    private Long comboId;

    @NotNull(message = "Order ID must not be null")
    @Min(value = 1, message = "Invalid order ID")
    private Long orderId;
}
