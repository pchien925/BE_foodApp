package vn.edu.hcmute.foodapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_cart_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends AbstractEntity<Long>{
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @OneToMany(mappedBy = "cartItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<CartItemOption> cartItemOptions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    private MenuItem menuItem;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price_at_addition", precision = 10, scale = 2)
    private BigDecimal priceAtAddition;
}
