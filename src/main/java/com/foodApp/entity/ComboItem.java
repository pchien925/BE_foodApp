package com.foodApp.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_combo_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComboItem extends AbstractEntity<Long> {
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne
    @JoinColumn(name = "combo_id")
    private Combo combo;
}
