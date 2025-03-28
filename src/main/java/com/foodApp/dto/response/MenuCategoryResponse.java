package com.foodApp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class MenuCategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;

    private Set<Long> itemIds;
}
