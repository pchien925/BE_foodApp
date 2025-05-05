package vn.edu.hcmute.foodapp.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmute.foodapp.util.enumeration.EDeliveryStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShipmentInfoResponse {
    private Long id;
    private EDeliveryStatus deliveryStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
