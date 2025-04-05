package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import vn.edu.hcmute.foodapp.util.EnumValue;
import vn.edu.hcmute.foodapp.util.enumeration.EPaymentMethod;

@Getter
public class CreateOrderRequest {
    @NotNull(message = "Branch ID cannot be null")
    @Min(value = 1, message = "Branch ID must be greater than 0")
    private Integer branchId;

    @NotBlank(message = "Shipping address cannot be blank")
    private String shippingAddress;

    private String note;

    @EnumValue(name = "paymentMethod", enumClass = EPaymentMethod.class, message = "Invalid payment method")
    private EPaymentMethod paymentMethod;
}
