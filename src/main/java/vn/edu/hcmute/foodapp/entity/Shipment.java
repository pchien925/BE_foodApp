package vn.edu.hcmute.foodapp.entity;

import vn.edu.hcmute.foodapp.util.enumeration.EDeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "tbl_shipment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment extends AbstractEntity<Long> {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false, unique = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User shipper;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EDeliveryStatus deliveryStatus;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ShipmentTrackingEvent> trackingEvents = new HashSet<>();
}
