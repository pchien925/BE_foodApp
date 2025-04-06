package vn.edu.hcmute.foodapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmute.foodapp.entity.Order;
import vn.edu.hcmute.foodapp.util.enumeration.EOrderStatus;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"user", "branch"})
    @Query("SELECT o FROM Order o " +
            "WHERE o.user.id = :userId " +
            "AND (:orderStatus IS NULL OR o.orderStatus = :orderStatus)")
    Page<Order> findByUser_IdAndOrderStatus(Long userId, EOrderStatus orderStatus, Pageable pageable);

    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.user u " +
            "LEFT JOIN FETCH o.branch b " +
            "LEFT JOIN FETCH o.orderItems oi " +
            "LEFT JOIN FETCH o.shipments s " +
            "LEFT JOIN FETCH o.payments p " +
            "LEFT JOIN FETCH o.loyaltyTransaction lt " +
            "LEFT JOIN FETCH oi.menuItem mi " +
            "LEFT JOIN FETCH oi.options op " +
            "WHERE o.id = :orderId")
    Optional<Order> findOrderWithDetailsById(Long orderId);

    @Query(value = "SELECT o FROM Order o LEFT JOIN o.user u LEFT JOIN o.branch b WHERE " +
            "(:statusFilter IS NULL OR o.orderStatus = :statusFilter) AND " +
            "(:userIdFilter IS NULL OR u.id = :userIdFilter) AND " +
            "(:branchIdFilter IS NULL OR b.id = :branchIdFilter) AND " +
            "(:orderCodeFilter IS NULL OR LOWER(o.orderCode) LIKE LOWER(CONCAT('%', :orderCodeFilter, '%')))",
            countQuery = "SELECT COUNT(o) FROM Order o LEFT JOIN o.user u LEFT JOIN o.branch b WHERE " +
                    "(:statusFilter IS NULL OR o.orderStatus = :statusFilter) AND " +
                    "(:userIdFilter IS NULL OR u.id = :userIdFilter) AND " +
                    "(:branchIdFilter IS NULL OR b.id = :branchIdFilter) AND " +
                    "(:orderCodeFilter IS NULL OR LOWER(o.orderCode) LIKE LOWER(CONCAT('%', :orderCodeFilter, '%')))")
    Page<Order> findOrdersAdminWithFilters(
            @Param("statusFilter") EOrderStatus statusFilter,
            @Param("userIdFilter") Long userIdFilter,
            @Param("branchIdFilter") Integer branchIdFilter,
            @Param("orderCodeFilter") String orderCodeFilter,
            Pageable pageable
    );
}
