package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("MENU_ITEM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuCartItem extends CartItem{
    @ManyToOne
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    private MenuItem menuItem;
}