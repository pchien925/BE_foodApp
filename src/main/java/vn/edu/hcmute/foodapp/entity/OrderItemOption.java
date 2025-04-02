package vn.edu.hcmute.foodapp.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_order_item_option")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemOption extends AbstractEntity<Long>{
    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(name = "option_name")
    private String optionName;

    @Column(name = "option_value")
    private String optionValue;

    @Column(name = "additional_price", precision = 10, scale = 2)
    private BigDecimal additionalPrice;
}
