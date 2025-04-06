package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.request.CreateOrderRequest;
import vn.edu.hcmute.foodapp.dto.response.OrderDetailsResponse;
import vn.edu.hcmute.foodapp.dto.response.OrderInfoResponse;
import vn.edu.hcmute.foodapp.dto.response.OrderSummaryResponse;
import vn.edu.hcmute.foodapp.entity.Order;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {UserMapper.class, BranchMapper.class, OrderItemMapper.class,
PaymentMapper.class, ShipmentMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(OrderMapper.class);

    Order toEntity(CreateOrderRequest orderRequest);

    OrderInfoResponse toInfoResponse(Order order);

    @Mapping(target = "userInfo", source = "user")
    @Mapping(target = "branchName", source = "branch.name")
    OrderSummaryResponse toSummaryResponse(Order order);

    @Mapping(target = "userInfo", source = "user")
    @Mapping(target = "branchInfo", source = "branch")
    @Mapping(target = "items", source = "orderItems")
    @Mapping(target = "paymentInfos", source = "payments")
    @Mapping(target = "shipmentInfo", source = "shipments")
    @Mapping(target = "pointsEarnedOrSpent", source = "loyaltyTransaction.pointsChange")
    @Mapping(target = "loyaltyTransactionDescription", source = "loyaltyTransaction.description")
    OrderDetailsResponse toDetailsResponse(Order order);
}
