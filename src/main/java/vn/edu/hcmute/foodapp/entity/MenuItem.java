package vn.edu.hcmute.foodapp.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @Column(name = "base_price", precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_available")
    @Builder.Default
    private Boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name= "menu_category_id", referencedColumnName = "id")
    private MenuCategory menuCategory;

    @OneToMany(mappedBy = "menuItem")
    @Builder.Default
    private Set<MenuItemOption> menuItemOptions = new HashSet<>();
}
