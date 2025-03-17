package com.foodApp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItemResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double basePrice;
    private Boolean available;
    private Long menuCategoryId;
}
