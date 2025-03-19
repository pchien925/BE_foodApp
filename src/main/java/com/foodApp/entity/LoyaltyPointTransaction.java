package com.foodApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tbl_loyalty_point_transaction")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyPointTransaction extends AbstractEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "loyalty_reward_id", nullable = false)
    private LoyaltyPoints loyaltyPoints;

    private Long points;
    
    private String transactionType;

    private String description;
}
