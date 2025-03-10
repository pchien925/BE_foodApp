package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_option_type")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionType extends AbstractEntity<Long> {
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @OneToMany(mappedBy = "optionType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OptionValue> optionValues = new HashSet<>();
}
