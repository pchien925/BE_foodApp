package com.foodApp.repository;

import com.foodApp.entity.OptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionTypeRepository extends JpaRepository<OptionType, Long> {
    List<OptionType> findAllByMenuItems_Id(Long menuItemId);
}
