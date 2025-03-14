package com.foodApp.repository;

import com.foodApp.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByMenuCategory_Id(Long id);
    Page<MenuItem> findByMenuCategory_Id(Long id, Pageable pageable);


    @Query("SELECT m FROM MenuItem m WHERE " +
            "(:q IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :q, '%'))) AND " +
            "(:category IS NULL OR m.menuCategory.name = :category) AND " +
            "(:minPrice IS NULL OR m.basePrice >= :minPrice) AND " +
            "(:maxPrice IS NULL OR m.basePrice <= :maxPrice)"
    )
    Page<MenuItem> searchMenuItems(
            @Param("q") String query,
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}
