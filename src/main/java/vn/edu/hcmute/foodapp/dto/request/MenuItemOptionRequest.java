package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuItemOptionRequest {
    @NotNull(message = "menu item id must not be null")
    @Min(value = 0, message = "menu item id must be greater than or equal to 0")
    private Long menuItemId;
    @NotNull(message = "option id must not be null")
    @Min(value = 0, message = "option id must be greater than or equal to 0")
    private Integer optionId;

    @NotEmpty(message = "value must not be null")
    private String value;

    @NotNull(message = "additional price must not be null")
    @Min(value = 0, message = "additional price must be greater than or equal to 0")
    private BigDecimal additionalPrice;
}
