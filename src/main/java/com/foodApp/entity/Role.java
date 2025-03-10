package com.foodApp.entity;


import com.foodApp.util.RoleName;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tbl_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity<Long> {
    @Enumerated(EnumType.STRING)
    private RoleName name;

    private String description;
}
