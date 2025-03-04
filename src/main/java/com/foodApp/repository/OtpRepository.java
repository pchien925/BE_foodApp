package com.foodApp.repository;

import com.foodApp.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

    Optional<Otp> findByCodeAndTypeAndUser_Id(String code, String type, Long userId);
}
