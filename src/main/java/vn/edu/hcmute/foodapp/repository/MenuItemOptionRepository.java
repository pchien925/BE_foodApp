package vn.edu.hcmute.foodapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmute.foodapp.entity.MenuItemOption;

import java.util.List;

@Repository
public interface MenuItemOptionRepository extends JpaRepository<MenuItemOption, Integer> {
    @EntityGraph(attributePaths = {"option"})
    List<MenuItemOption> findByMenuItem_Id(Long id);

    @Query("SELECT mio FROM MenuItemOption mio JOIN FETCH mio.option WHERE mio.id IN :ids")
    List<MenuItemOption> findAllByIdWithOption(@Param("ids") List<Integer> ids);

    @Query("select mio FROM MenuItemOption mio JOIN FETCH mio.option WHERE " +
            "mio.id IN :ids AND mio.menuItem.id = :menuItemId")
    List<MenuItemOption> findAllByIdsAndMenuItem_Id(List<Integer> ids, Long menuItemId);
}
