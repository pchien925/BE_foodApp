package com.foodApp.repository;

import com.foodApp.entity.ComboItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComboItemRepository extends JpaRepository<ComboItem, Long> {
    List<ComboItem> findByCombo_Id(Long comboId);
}
