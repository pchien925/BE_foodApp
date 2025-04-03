package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuItemRequest {
    @NotEmpty(message = "name must not be empty")
    private String name;

    @NotNull(message = "base price must not be null")
    @Min(value = 0, message = "base price must be greater than or equal to 0")
    private BigDecimal basePrice;

    private String description;

    private String imageUrl;

    private Boolean isAvailable;

    private Integer menuCategoryId;
}
