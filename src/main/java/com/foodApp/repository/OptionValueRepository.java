package com.foodApp.repository;

import com.foodApp.entity.MenuItem;
import com.foodApp.entity.OptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OptionValueRepository extends JpaRepository<OptionValue, Long> {
    Optional<OptionValue> findByOptionType_Id(Long optionTypeId);

    Set<OptionValue> findByIdInAndOptionType_MenuItems_Id(Collection<Long> ids, Long id);
}
