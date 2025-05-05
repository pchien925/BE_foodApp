package vn.edu.hcmute.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmute.foodapp.entity.LoyaltyPointTransaction;

@Repository
public interface LoyaltyPointTransactionRepository extends JpaRepository<LoyaltyPointTransaction, Long> {

    boolean existsByOrder_Id(Long id);
}
