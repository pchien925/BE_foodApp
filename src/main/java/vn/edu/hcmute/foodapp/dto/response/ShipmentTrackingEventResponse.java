package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmute.foodapp.util.enumeration.EDeliveryStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ShipmentTrackingEventResponse {
    private EDeliveryStatus deliveryStatus;

    private String note;

    private LocalDateTime eventTime;

    private Double locationLatitude;

    private Double locationLongitude;
}
