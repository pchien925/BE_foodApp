package com.foodApp.repository;

import com.foodApp.entity.MenuItem;
import com.foodApp.entity.Order;
import com.foodApp.util.OrderStatus;
import com.foodApp.util.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndUser_Email(Long id, String email);

    Page<Order> findByUser_Email(String email, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE " +
            "(:query IS NULL OR " +
            "LOWER(o.deliveryName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(o.deliveryPhone) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(o.deliveryAddress) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "(:status IS NULL OR o.orderStatus = :status) AND " +
            "(:type IS NULL OR o.orderType = :type) AND " +
            "(:minAmount IS NULL OR o.totalAmount >= :minAmount) AND " +
            "(:maxAmount IS NULL OR o.totalAmount <= :maxAmount) AND " +
            "(:userId IS NULL OR o.user.id = :userId) AND " +
            "(:branchId IS NULL OR o.branch.id = :branchId)")
    Page<Order> searchOrders(
            @Param("query") String query,
            @Param("status") OrderStatus status,
            @Param("type") OrderType type,
            @Param("minAmount") Double minAmount,
            @Param("maxAmount") Double maxAmount,
            @Param("userId") Long userId,
            @Param("branchId") Long branchId,
            Pageable pageable
    );

}
