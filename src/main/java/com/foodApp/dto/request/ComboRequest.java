package com.foodApp.dto.request;

import com.foodApp.entity.ComboItem;
import com.foodApp.entity.MenuCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class ComboRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @Min(value = 0, message = "Price must be greater than 0")
    private Double price;

    @Builder.Default
    private Boolean available = false;

    @Min(value = 0, message = "Invalid menu category id data")
    private Long menuCategoryId;
}
