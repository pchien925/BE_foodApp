package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.CreateOrderRequest;
import vn.edu.hcmute.foodapp.dto.response.*;
import vn.edu.hcmute.foodapp.service.OrderService;
import vn.edu.hcmute.foodapp.util.enumeration.EOrderStatus;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API for managing orders")
@Slf4j(topic = "ORDER_CONTROLLER")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseData<OrderInfoResponse> createOrder(@RequestParam Long userId, @RequestBody CreateOrderRequest request) {
        log.info("Create order for user with id: {}", userId);
        OrderInfoResponse orderInfoResponse = orderService.createOrder(userId, request);
        return ResponseData.<OrderInfoResponse>builder()
                .data(orderInfoResponse)
                .message("Order created successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<OrderDetailsResponse> getOrderDetails(@PathVariable Long id, @RequestParam Long userId) {
        log.info("Get order details for order with id: {}", id);
        OrderDetailsResponse orderDetailsResponse = orderService.getOrderDetailForUser(userId, id);
        return ResponseData.<OrderDetailsResponse>builder()
                .data(orderDetailsResponse)
                .message("Order details retrieved successfully")
                .build();
    }

    @GetMapping
    public ResponseData<PageResponse<OrderSummaryResponse>> getUserOrders(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) EOrderStatus statusFilter) {
        log.info("Get orders for user with id: {}", userId);
        PageResponse<OrderSummaryResponse> orderSummaryResponsePage = orderService.getUserOrders(userId, page, size, sort, direction, statusFilter);
        return ResponseData.<PageResponse<OrderSummaryResponse>>builder()
                .data(orderSummaryResponsePage)
                .message("Orders retrieved successfully")
                .build();
    }
}
