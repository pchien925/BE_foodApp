package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmute.foodapp.util.enumeration.EPaymentMethod;
import vn.edu.hcmute.foodapp.util.enumeration.EPaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentInfoResponse {
    private Long id;

    private EPaymentMethod paymentMethod;

    private EPaymentStatus paymentStatus;

    private BigDecimal amount;

    private LocalDateTime paidAt;

    private String transactionCode;
}
