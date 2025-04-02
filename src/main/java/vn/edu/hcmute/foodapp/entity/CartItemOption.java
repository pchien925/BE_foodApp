package vn.edu.hcmute.foodapp.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_cart_item_option")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemOption extends AbstractEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "cart_item_id", referencedColumnName = "id")
    private CartItem cartItem;

    @ManyToOne
    @JoinColumn(name = "menu_item_option_id", referencedColumnName = "id")
    private MenuItemOption menuItemOption;
}
