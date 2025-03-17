package com.foodApp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_menu_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem extends AbstractEntity<Long>{
    private String name;

    private String description;

    private String imageUrl;

    private Double basePrice;

    private Boolean available;

    @ManyToMany
    @JoinTable(
            name = "tbl_menu_item_option_type",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "option_type_id")
    )
    @Builder.Default
    private Set<OptionType> optionTypes = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;
}
