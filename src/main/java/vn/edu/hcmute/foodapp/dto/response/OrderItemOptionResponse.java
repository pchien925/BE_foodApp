package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderItemOptionResponse {
    private Long id;
    private String optionName;
    private String optionValue;
    private BigDecimal additionalPrice;
}
