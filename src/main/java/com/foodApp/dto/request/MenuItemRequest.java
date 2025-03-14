package com.foodApp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuItemRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @Min(value = 0, message = "Base price must be greater than 0")
    private Double basePrice;

    @Builder.Default
    private Boolean available = false;

    @Min(value = 0, message = "Invalid data")
    private Long menuCategoryId;
}
