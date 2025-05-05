package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmute.foodapp.util.enumeration.EOrderStatus;
import vn.edu.hcmute.foodapp.util.enumeration.EPaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class OrderDetailsResponse {
    private Long id;

    private String orderCode;

    private EOrderStatus orderStatus;

    private BigDecimal totalPrice;

    private String note;

    private String shippingAddress;

    private EPaymentMethod paymentMethod;

    private UserInfoResponse user;

    private BranchInfoResponse branch;

    private Set<OrderItemResponse> items;

    private Set<PaymentInfoResponse> payments;

    private ShipmentDetailResponse shipments;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
