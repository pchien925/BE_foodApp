package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NotificationResponse {
    private Long id;
    private String title;
    private String content;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
}
