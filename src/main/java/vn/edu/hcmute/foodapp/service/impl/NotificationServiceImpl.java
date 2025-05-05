package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.NotificationRequest;
import vn.edu.hcmute.foodapp.dto.response.NotificationResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.entity.Notification;
import vn.edu.hcmute.foodapp.entity.User;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.NotificationMapper;
import vn.edu.hcmute.foodapp.repository.NotificationRepository;
import vn.edu.hcmute.foodapp.repository.UserRepository;
import vn.edu.hcmute.foodapp.service.NotificationService;
import vn.edu.hcmute.foodapp.util.PaginationUtil;
import vn.edu.hcmute.foodapp.util.SecurityUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void sendAndSaveNotification(NotificationRequest request, String email) {
        log.info("Sending notification to email {}: {}", email, request);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        Notification notification = Notification.builder()
                .content(request.getContent())
                .title(request.getTitle())
                .userId(user)
                .build();
        notificationRepository.save(notification);
        var message = NotificationMapper.INSTANCE.toResponse(notification);

        messagingTemplate.convertAndSendToUser(email, "/queue/notifications", message);
    }

    @Override
    public void sendNotificationToAllUsers(NotificationRequest request) {
        log.info("Sending notification to all users: {}", request);

        messagingTemplate.convertAndSend("/topic/notifications", request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<NotificationResponse> getUserNotifications(Long userId, int page, int size, String sort, String direction) {
        log.info("Fetching all notifications");

        if (SecurityUtil.getCurrentUser().getId() != userId) {
            throw new AccessDeniedException("Access denied");
        }

        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Notification> notificationPage = notificationRepository.findByUserId_IdOrUserId_IdNull(userId, pageable);
        return PageResponse.<NotificationResponse>builder()
                .content(notificationPage.getContent().stream()
                        .map(NotificationMapper.INSTANCE::toResponse)
                        .toList())
                .currentPage(page)
                .pageSize(size)
                .totalElements(notificationPage.getTotalElements())
                .totalPages(notificationPage.getTotalPages())
                .build();
    }

    @Transactional
    @Override
    public NotificationResponse markNotificationAsRead(Long notificationId, Long userId) {
        log.info("Marking notification as read: {}", notificationId);

        if (SecurityUtil.getCurrentUser().getId() != userId) {
            throw new AccessDeniedException("Access denied");
        }

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        notification.setIsRead(true);
        notificationRepository.save(notification);

        return NotificationMapper.INSTANCE.toResponse(notification);
    }
}
