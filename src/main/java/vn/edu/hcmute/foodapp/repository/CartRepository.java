package vn.edu.hcmute.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmute.foodapp.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
