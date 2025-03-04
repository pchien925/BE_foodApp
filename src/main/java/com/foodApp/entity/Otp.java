package com.foodApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_otp")
public class Otp extends AbstractEntity<Integer>{
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "VARCHAR(6)")
    private String code;

    @Column(nullable = false, name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, name = "is_used")
    @Builder.Default
    private boolean isUsed = false;

    @Column(name = "data")
    private String data;
}
