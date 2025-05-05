package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.NotificationRequest;
import vn.edu.hcmute.foodapp.dto.response.NotificationResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.NotificationService;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "Notification API for authenticated users")
@Slf4j(topic = "NOTIFICATION_CONTROLLER")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseData<PageResponse<NotificationResponse>> getNotifications(
            @RequestParam Long userId,
             @RequestParam(defaultValue = "1") int page,
             @RequestParam(defaultValue = "10") int size,
             @RequestParam(defaultValue = "createdAt") String sort,
             @RequestParam(defaultValue = "desc") String direction
    ){
        log.info("Fetching notifications for the authenticated user");
        PageResponse<NotificationResponse> notificationPage = notificationService.getUserNotifications(userId, page, size, sort, direction);
        return ResponseData.<PageResponse<NotificationResponse>>builder()
                .data(notificationPage)
                .message("Notifications retrieved successfully")
                .build();
    }

    @PutMapping("/{id}/read")
    public ResponseData<NotificationResponse> markNotificationAsRead(@PathVariable Long id,
                                                                     @RequestParam Long userId) {
        log.info("Marking notification ID {} as read", id);
        NotificationResponse notification = notificationService.markNotificationAsRead(id, userId);
        return ResponseData.<NotificationResponse>builder()
                .data(notification)
                .message("Notification marked as read successfully")
                .build();
    }

    @PostMapping("/send/all")
    public ResponseData<Void> sendNotificationToAllUsers(@RequestBody NotificationRequest request) {
        log.info("Sending notification to all users");
        notificationService.sendNotificationToAllUsers(request);
        return ResponseData.<Void>builder()
                .message("Notification sent to all users successfully")
                .build();
    }

}
