package vn.edu.hcmute.foodapp.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_cart")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends AbstractEntity<Long>{
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "session_id")
    private String sessionId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();
}
