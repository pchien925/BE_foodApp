package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class MenuItemOptionResponse {
    private Integer id;

    private String value;

    private BigDecimal additionalPrice;
}
