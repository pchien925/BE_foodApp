package vn.edu.hcmute.foodapp.entity;

import vn.edu.hcmute.foodapp.util.enumeration.ELoyaltyTransactionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_loyalty_point_transaction")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyPointTransaction extends AbstractEntity<Long>{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = true)
    private Order order;

    @Column(name = "points_change")
    private Integer pointsChange;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private ELoyaltyTransactionType transactionType;

    @Column(name = "description")
    private String description;
}
