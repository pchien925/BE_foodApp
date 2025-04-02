package vn.edu.hcmute.foodapp.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_menu_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem extends AbstractEntity<Long> {
    @Column(name = "name")
    private String name;

    @Column(name = "base_price")
    private Float basePrice;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name= "menuCategory_id", referencedColumnName = "id")
    private MenuCategory menuCategory;

    @OneToMany(mappedBy = "menuItem")
    @Builder.Default
    private Set<MenuItemOption> menuItemOptions = new HashSet<>();
}
