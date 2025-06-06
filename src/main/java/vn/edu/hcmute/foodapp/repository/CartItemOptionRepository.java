package vn.edu.hcmute.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmute.foodapp.entity.CartItem;
import vn.edu.hcmute.foodapp.entity.CartItemOption;

@Repository
public interface CartItemOptionRepository extends JpaRepository<CartItemOption, Long> {

    void deleteByCartItem(CartItem cartItem);
}
