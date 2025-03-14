package com.foodApp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ComboItemResponse {
    private Long id;
    private Integer quantity;
    private Long comboId;
    private MenuItemResponse menuItem;
}
