package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_order_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends AbstractEntity<Long> {
    private Integer quantity;

    @Column(name = "price_at_order")
    private Double priceAtOrder;

    private String note;

    @Column(name = "is_reward_item", nullable = false)
    @Builder.Default
    private boolean isRewardItem = false;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    private MenuItem menuItem;

    @ManyToMany
    @JoinTable(
            name = "tbl_order_item_option_value",
            joinColumns = @JoinColumn(name = "order_item_id"),
            inverseJoinColumns = @JoinColumn(name = "option_value_id")
    )
    private Set<OptionValue> selectedOptions = new HashSet<>();
}