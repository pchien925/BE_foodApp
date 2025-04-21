package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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

    private BigDecimal totalPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String note;

    private String shippingAddress;

    private EPaymentMethod paymentMethod;

    private UserInfoResponse userInfo;

    private BranchInfoResponse branchInfo;

    private Set<OrderItemResponse> items;

    private Set<PaymentInfoResponse> paymentInfos;

    private Integer pointsEarnedOrSpent;

    private String loyaltyTransactionDescription;
}
