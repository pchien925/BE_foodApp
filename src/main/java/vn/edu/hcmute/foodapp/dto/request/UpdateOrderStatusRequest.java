package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import vn.edu.hcmute.foodapp.util.EnumValue;
import vn.edu.hcmute.foodapp.util.enumeration.EOrderStatus;

@Getter
public class UpdateOrderStatusRequest {
    @NotNull(message = "New status cannot be null")
    @EnumValue(name = "orderStatus", enumClass = EOrderStatus.class, message = "Invalid order status")
    private EOrderStatus status;

    private String reason;
}
