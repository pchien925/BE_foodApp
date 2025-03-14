package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("COMBO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComboOrderItem extends OrderItem{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "combo_id", referencedColumnName = "id")
    private Combo combo;
}