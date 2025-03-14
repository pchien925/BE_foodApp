package com.foodApp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class ComboResponse {
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Boolean available;
    private Set<Long> itemIds;
    private Long menuCategoryId;
}
