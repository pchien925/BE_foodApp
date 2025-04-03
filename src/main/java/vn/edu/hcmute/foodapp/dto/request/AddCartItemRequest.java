package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class AddCartItemRequest {
    @NotEmpty(message = "menu item id must not be empty")
    @Min(value = 1, message = "menu item id must be greater than 0")
    Long menuItemId;

    @NotEmpty(message = "quantity must not be empty")
    @Min(value = 1, message = "quantity must be greater than 0")
    Integer quantity;

    List<Long> selectedMenuItemOptionIds;
}
