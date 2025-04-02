package vn.edu.hcmute.foodapp.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "tbl_option")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option extends AbstractEntity<Integer> {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<MenuItemOption> menuItemOptions = new HashSet<>();
}
