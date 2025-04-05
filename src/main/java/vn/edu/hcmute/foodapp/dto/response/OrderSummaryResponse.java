package vn.edu.hcmute.foodapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmute.foodapp.util.enumeration.EOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderSummaryResponse {
    private Long id;
    private String orderCode;
    private EOrderStatus orderStatus;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private String branchName;
    private UserInfoResponse userInfo;
}
