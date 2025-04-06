package vn.edu.hcmute.foodapp.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.UpdateOrderStatusRequest;
import vn.edu.hcmute.foodapp.dto.response.*;
import vn.edu.hcmute.foodapp.service.OrderService;
import vn.edu.hcmute.foodapp.util.enumeration.EOrderStatus;

@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
@Tag(name = "Order (Admin)", description = "Order management API for Admin/Staff")
@Slf4j(topic = "ADMIN_ORDER_CONTROLLER")
public class AdminOrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseData<PageResponse<OrderSummaryResponse>> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction,
            // Bộ lọc cho Admin
            @RequestParam(required = false) EOrderStatus statusFilter,
            @RequestParam(required = false) Long userIdFilter,
            @RequestParam(required = false) Integer branchIdFilter,
            @RequestParam(required = false) String orderCodeFilter
    ) {
        log.info("Admin/Staff request: Get all orders with filters. Page: {}, Size: {}, Status: {}, User: {}, Branch: {}, Code: {}",
                page, size, statusFilter, userIdFilter, branchIdFilter, orderCodeFilter);
        PageResponse<OrderSummaryResponse> orderPage = orderService.getAllOrdersAdmin(
                page, size, sort, direction, statusFilter, userIdFilter, branchIdFilter, orderCodeFilter);
        return ResponseData.<PageResponse<OrderSummaryResponse>>builder()
                .data(orderPage)
                .message("Admin orders retrieved successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<OrderDetailsResponse> getOrderDetails(@PathVariable Long id) {
        log.info("Admin/Staff request: Get details for order ID: {}", id);
        OrderDetailsResponse orderDetails = orderService.getOrderDetailsAdmin(id);
        return ResponseData.<OrderDetailsResponse>builder()
                .data(orderDetails)
                .message("Admin order details retrieved successfully")
                .build();
    }

    @PatchMapping("/{id}/status")
    public ResponseData<OrderInfoResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        log.info("Admin/Staff request: Update status for order ID: {} to {}", id, request.getStatus());
        OrderInfoResponse updatedOrder = orderService.updateOrderStatusAdmin(id, request);
        return ResponseData.<OrderInfoResponse>builder()
                .data(updatedOrder)
                .message("Order status updated successfully by admin/staff")
                .build();
    }
}
