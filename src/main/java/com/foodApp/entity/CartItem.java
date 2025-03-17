package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "cart_item_type", discriminatorType = DiscriminatorType.STRING)
public abstract class CartItem extends AbstractEntity<Long>{
    private Integer quantity;

    private String note;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToMany
    @JoinTable(
            name = "tbl_cart_item_option_value",
            joinColumns = @JoinColumn(name = "cart_item_id"),
            inverseJoinColumns = @JoinColumn(name = "option_value_id")
    )
    private Set<OptionValue> selectedOptions;
}
