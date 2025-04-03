package vn.edu.hcmute.foodapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmute.foodapp.entity.MenuItemOption;

import java.util.List;

@Repository
public interface MenuItemOptionRepository extends JpaRepository<MenuItemOption, Integer> {
    @EntityGraph(attributePaths = {"option"})
    List<MenuItemOption> findByMenuItem_Id(Long id);
}
