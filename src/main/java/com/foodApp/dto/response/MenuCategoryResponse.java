package com.foodApp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foodApp.entity.Combo;
import com.foodApp.entity.MenuItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
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

    private Set<Long> comboIds;
}
