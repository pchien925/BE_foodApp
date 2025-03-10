package com.foodApp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_loyalty_reward")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyReward extends AbstractEntity<Long> {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_points")
    private Long totalPoints;

    @Column(name = "membership_tier")
    private String membershipTier;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

}
