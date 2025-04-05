package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmute.foodapp.util.enumeration.EDeliveryStatus;

import java.util.Set;

@Getter
@Setter
@Builder
public class ShipmentInfoResponse {
    private Long id;

    private EDeliveryStatus deliveryStatus;

    private Set<ShipmentTrackingEventResponse> trackingHistory;
}
