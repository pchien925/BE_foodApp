package vn.edu.hcmute.foodapp.dto.request;

import lombok.Getter;
import vn.edu.hcmute.foodapp.util.EnumValue;
import vn.edu.hcmute.foodapp.util.enumeration.EPaymentMethod;

import java.math.BigDecimal;

@Getter
public class PaymentRequest {
    private Long orderId;

    @EnumValue(name = "paymentMethod", enumClass = EPaymentMethod.class)
    private String paymentMethod;

    private BigDecimal amount;

    private String transactionCode;

    private String gatewayResponse;
}
