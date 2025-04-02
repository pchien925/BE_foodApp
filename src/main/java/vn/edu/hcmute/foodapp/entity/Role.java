package vn.edu.hcmute.foodapp.entity;


import jakarta.persistence.*;
import vn.edu.hcmute.foodapp.util.enumeration.ERoleName;
import lombok.*;

@Entity
@Table(name = "tbl_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity<Long> {
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private ERoleName name;

    @Column(name = "description")
    private String description;
}
