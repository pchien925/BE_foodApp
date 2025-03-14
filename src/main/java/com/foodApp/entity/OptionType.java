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

    @ManyToMany(mappedBy = "optionTypes")
    @Builder.Default
    private Set<MenuItem> menuItems = new HashSet<>();

    @OneToMany(mappedBy = "optionType", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OptionValue> optionValues = new HashSet<>();
}
