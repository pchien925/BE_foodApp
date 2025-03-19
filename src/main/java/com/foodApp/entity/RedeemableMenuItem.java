package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "tbl_redeemable_menu_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedeemableMenuItem extends AbstractEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(name = "points_required", nullable = false)
    private Long pointsRequired;

    @Column(name = "available", nullable = false)
    private boolean available;
}