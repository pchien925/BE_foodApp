package com.foodApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tbl_user_has_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHasRole extends AbstractEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
