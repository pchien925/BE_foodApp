package vn.edu.hcmute.foodapp.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_menu_category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategory extends AbstractEntity<Integer> {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "menuCategory")
    @Builder.Default
    private Set<MenuItem> menuItems = new HashSet<>();
}
