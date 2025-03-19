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
public class LoyaltyPoints extends AbstractEntity<Long> {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_points", nullable = false)
    @Builder.Default
    private Long totalPoints = 0L;

    @Column(name = "accumulated_points", nullable = false)
    @Builder.Default
    private Long accumulatedPoints = 0L;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;
}
