package vn.edu.hcmute.foodapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_notification")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends AbstractEntity<Long> {
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "is_read")
    @Builder.Default
    private Boolean isRead = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;
}
