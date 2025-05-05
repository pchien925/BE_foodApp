package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmute.foodapp.util.enumeration.EOrderStatus;
import vn.edu.hcmute.foodapp.util.enumeration.EPaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderInfoResponse {
    private Long id;
    private String orderCode;
    private EOrderStatus orderStatus;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private EPaymentMethod paymentMethod;
    private ShipmentInfoResponse shipments;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
