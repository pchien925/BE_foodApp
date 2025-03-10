package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_combo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Combo extends AbstractEntity<Long> {
    private String name;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    private double price;
    private boolean available;

    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ComboItem> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;
}
