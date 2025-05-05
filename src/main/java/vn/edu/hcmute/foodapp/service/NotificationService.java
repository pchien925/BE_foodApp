package vn.edu.hcmute.foodapp.service;

import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.NotificationRequest;
import vn.edu.hcmute.foodapp.dto.response.NotificationResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;

public interface NotificationService {
    void sendAndSaveNotification(NotificationRequest request, String username);

    void sendNotificationToAllUsers(NotificationRequest request);

    PageResponse<NotificationResponse> getUserNotifications(Long userId, int page, int size, String sort, String direction);

    NotificationResponse markNotificationAsRead(Long notificationId, Long userId);
}
