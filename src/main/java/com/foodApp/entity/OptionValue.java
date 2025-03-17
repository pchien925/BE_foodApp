package com.foodApp.entity;

import jakarta.persistence.*;
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

    @Column(name = "extra_cost")
    private Double extraCost;

    @Builder.Default
    private Boolean available = true;

    @ManyToOne
    @JoinColumn(name = "option_type_id")
    private OptionType optionType;
}
