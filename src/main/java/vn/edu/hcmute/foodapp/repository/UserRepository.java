package vn.edu.hcmute.foodapp.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import vn.edu.hcmute.foodapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
