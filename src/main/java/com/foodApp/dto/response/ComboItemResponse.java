package com.foodApp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComboItemResponse {
    private Long id;
    private Integer quantity;
    private Double priceAtOrder;
    private String note;
    private Long comboId;
    private MenuItemResponse menuItem;
    private ComboItemResponse comboItem;
}
