package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_menu_category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategory extends AbstractEntity<Long> {
    private String name;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "menuCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<MenuItem> items = new HashSet<>();

}
