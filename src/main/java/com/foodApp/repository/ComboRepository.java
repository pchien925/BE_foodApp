package com.foodApp.repository;

import com.foodApp.entity.Combo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long> {
    Page<Combo> findByMenuCategoryId(Long id, Pageable pageable);

    @Query("SELECT m FROM Combo m WHERE " +
            "(:q IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :q, '%'))) AND " +
            "(:category IS NULL OR m.menuCategory.name = :category) AND " +
            "(:minPrice IS NULL OR m.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR m.price <= :maxPrice)"
    )
    Page<Combo> searchCombos(
            @Param("q") String query,
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}
