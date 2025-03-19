package com.foodApp.dto.request;

import com.foodApp.entity.MenuItem;
import com.foodApp.entity.OptionValue;
import com.foodApp.entity.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class OrderItemRequest {
    @NotNull
    @Min(value = 1, message = "Invalid quantity")
    private Integer quantity;

    private String note;

    private Long menuItemId;

    @Builder.Default
    private Set<Long> selectedOptionIds = new HashSet<>();
}
