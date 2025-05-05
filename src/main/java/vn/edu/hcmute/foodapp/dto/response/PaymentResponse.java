package vn.edu.hcmute.foodapp.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmute.foodapp.util.enumeration.EPaymentMethod;
import vn.edu.hcmute.foodapp.util.enumeration.EPaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentResponse {
    private Long id;
    private BigDecimal amount;
    private EPaymentStatus status;
    private String transactionCode;
    private EPaymentMethod paymentMethod;
    private String gatewayResponse;
    private LocalDateTime paidAt;

    private OrderInfoResponse order;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
