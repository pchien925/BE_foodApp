package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_order_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends AbstractEntity<Long> {
    private Integer quantity;
    private Double priceAtOrder;
    private String note;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    private MenuItem menuItem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "combo_id", referencedColumnName = "id")
    private Combo combo;
}
