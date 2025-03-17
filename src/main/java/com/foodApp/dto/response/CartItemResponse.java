package com.foodApp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemResponse {
    private Long id;
    private Integer quantity;
    private String note;
    private MenuItemResponse menuItem;
}
