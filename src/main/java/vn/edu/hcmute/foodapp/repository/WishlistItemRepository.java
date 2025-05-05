package vn.edu.hcmute.foodapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmute.foodapp.entity.WishlistItem;

import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {


    Page<WishlistItem> findByUser_Id(Long userId, Pageable pageable);

    Optional<WishlistItem> findByUser_IdAndMenuItem_Id(Long userId, Long menuItemId);
}
