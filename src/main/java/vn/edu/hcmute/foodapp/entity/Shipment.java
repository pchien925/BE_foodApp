package vn.edu.hcmute.foodapp.entity;

import vn.edu.hcmute.foodapp.util.enumeration.EDeliveryStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tbl_shipment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment extends AbstractEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User shipper;

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private EDeliveryStatus deliveryStatus;
}
