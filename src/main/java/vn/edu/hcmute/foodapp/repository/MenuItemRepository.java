package vn.edu.hcmute.foodapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.edu.hcmute.foodapp.entity.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("SELECT mi FROM MenuItem mi WHERE " +
            "(:isAvailable IS NULL OR mi.isAvailable = :isAvailable) AND " +
            "(:keyword IS NULL OR " +
            "LOWER(mi.name) LIKE LOWER('%' || :keyword || '%') OR " +
            "LOWER(mi.description) LIKE LOWER('%' || :keyword || '%')) AND " +
            "(:menuCategoryId IS NULL OR mi.menuCategory.id = :menuCategoryId)")
    Page<MenuItem> searchMenuItems(
            @Param("keyword") String keyword,
            @Param("menuCategoryId") Integer menuCategoryId,
            @Param("isAvailable") Boolean isAvailable,
            Pageable pageable);

    @Query("SELECT m FROM MenuItem m WHERE" +
            " :isAvailable IS NULL OR m.isAvailable = :isAvailable")
    Page<MenuItem> findByIsAvailable(
            @Param("isAvailable") Boolean isAvailable,
            Pageable pageable);

    @Query("SELECT m FROM MenuItem m WHERE " +
            "m.isAvailable = true " +
            "AND (:menuCategoryId IS NULL OR m.menuCategory.id = :menuCategoryId)")
    Page<MenuItem> findByIsAvailableTrue(
            @Param("menuCategoryId") Integer menuCategoryId,
            Pageable pageable);
}
