package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "order_item_type", discriminatorType = DiscriminatorType.STRING)
public abstract class OrderItem extends AbstractEntity<Long> {
    private Integer quantity;

    @Column(name = "price_at_order")
    private Double priceAtOrder;
    private String note;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
