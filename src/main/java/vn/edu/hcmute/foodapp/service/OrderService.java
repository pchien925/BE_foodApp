package vn.edu.hcmute.foodapp.service;

import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.CreateOrderRequest;
import vn.edu.hcmute.foodapp.dto.response.OrderDetailsResponse;
import vn.edu.hcmute.foodapp.dto.response.OrderInfoResponse;
import vn.edu.hcmute.foodapp.dto.response.OrderSummaryResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.util.enumeration.EOrderStatus;

public interface OrderService {
    OrderInfoResponse createOrder(Long userId, CreateOrderRequest request);

    PageResponse<OrderSummaryResponse> getUserOrders(Long userId, int page, int size, String sort, String direction, EOrderStatus statusFilter);

    OrderDetailsResponse getOrderDetailForUser(Long userId, Long orderId);
}
