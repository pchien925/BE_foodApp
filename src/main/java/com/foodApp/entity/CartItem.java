package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_cart_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends AbstractEntity<Long>{
    private Integer quantity;

    private String note;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    private MenuItem menuItem;

    @ManyToMany
    @JoinTable(
            name = "tbl_cart_item_option_value",
            joinColumns = @JoinColumn(name = "cart_item_id"),
            inverseJoinColumns = @JoinColumn(name = "option_value_id")
    )
    @Builder.Default
    private Set<OptionValue> selectedOptions = new HashSet<>();
}
