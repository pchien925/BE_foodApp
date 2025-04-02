package vn.edu.hcmute.foodapp.entity;
import vn.edu.hcmute.foodapp.util.enumeration.EReviewStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tbl_menu_item_review")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemReview extends AbstractEntity<Long>{
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    private MenuItem menuItem;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EReviewStatus status;
}
