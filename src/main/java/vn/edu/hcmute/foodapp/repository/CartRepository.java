package vn.edu.hcmute.foodapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmute.foodapp.entity.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN c.user u " +
            "LEFT JOIN FETCH c.cartItems ci " +
            "LEFT JOIN FETCH ci.menuItem mi " +
            "LEFT JOIN FETCH ci.cartItemOptions cio " +
            "LEFT JOIN FETCH cio.menuItemOption mio " +
            "LEFT JOIN FETCH mio.option o " +
            "WHERE u.id = :userId")
    Optional<Cart> findFullCartByUser_Id(@Param("userId") Long userId);

    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN FETCH c.cartItems ci " +
            "LEFT JOIN FETCH ci.menuItem mi " +
            "LEFT JOIN FETCH ci.cartItemOptions cio " +
            "LEFT JOIN FETCH cio.menuItemOption mio " +
            "LEFT JOIN FETCH mio.option o " +
            "WHERE c.sessionId = :sessionId")
    Optional<Cart> findByFullCartSessionId(@Param("sessionId") String sessionId);

    boolean existsByUser_IdOrSessionId(Long userId, String sessionId);

}
