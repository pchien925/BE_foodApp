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
public class MenuItem extends AbstractEntity<Long> {
    private String name;

    private String description;

    private String imageUrl;

    private double basePrice;

    private boolean available;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OptionType> optionTypes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;
}
