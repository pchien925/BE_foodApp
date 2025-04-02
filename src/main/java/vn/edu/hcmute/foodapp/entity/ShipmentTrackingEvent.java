package vn.edu.hcmute.foodapp.entity;
import vn.edu.hcmute.foodapp.util.enumeration.EDeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "tbl_shipment_tracking_event")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentTrackingEvent extends AbstractEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private EDeliveryStatus deliveryStatus;

    @Column(name = "event_time")
    private LocalDateTime eventTime;

    @Column(name = "note")
    private String note;

    @Column(name = "location_Latitude")
    private Double locationLatitude;

    @Column(name = "location_Longitude")
    private Double locationLongitude;
}
