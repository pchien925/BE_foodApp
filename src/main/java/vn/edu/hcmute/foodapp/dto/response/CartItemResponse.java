package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
public class CartItemResponse {
    private Long id;
    private Integer quantity;
    private BigDecimal priceAtAddition;
    private MenuItemResponse menuItem;
    private Set<MenuItemOptionResponse> selectedOptions;
}
