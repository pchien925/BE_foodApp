package com.foodApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tbl_option_value")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionValue extends AbstractEntity<Long>{
    private String name;
    private String description;
    private double extraCost;
    private boolean available;

    @Builder.Default
    private boolean defaultOption = false;

    @ManyToOne
    @JoinColumn(name = "option_type_id")
    private OptionType optionType;
}
