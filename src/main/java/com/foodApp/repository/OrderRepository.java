package com.foodApp.repository;

import com.foodApp.entity.MenuItem;
import com.foodApp.entity.Order;
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

//    @Query("SELECT o FROM Order o WHERE " +
//            "(:q IS NULL OR LOWER(o.orderStatus) LIKE LOWER(CONCAT('%', :q, '%'))) AND " +
//            "(:category IS NULL OR m.menuCategory.name = :category) AND " +
//            "(:minPrice IS NULL OR m.basePrice >= :minPrice) AND " +
//            "(:maxPrice IS NULL OR m.basePrice <= :maxPrice)"
//    )
//    Page<Order> searchMenuItems(
//            @Param("q") String query,
//            Pageable pageable
//    );

}
