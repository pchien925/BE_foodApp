package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
public class CartResponse {
    private Long id;

    private Long userId;

    private String sessionId;

    private Set<CartItemResponse> cartItems;

    private BigDecimal totalPrice;
}
