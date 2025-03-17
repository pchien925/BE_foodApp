package com.foodApp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private Double priceAtOrder;
    private String note;
    private MenuItemResponse menuItem;
    private Set<OptionValueResponse> selectedOptions;
}
