package vn.edu.hcmute.foodapp.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_menu_item_option")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemOption extends AbstractEntity<Integer> {
    @ManyToOne
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    private MenuItem menuItem;

    @ManyToOne
    @JoinColumn(name = "option_id", referencedColumnName = "id")
    private Option option;

    @Column(name = "value")
    private String value;

    @Column(name = "additional_price", precision = 10, scale = 2)
//    @PositiveOrZero
    private BigDecimal additionalPrice;
}
