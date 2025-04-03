package vn.edu.hcmute.foodapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmute.foodapp.entity.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

    @Query("SELECT b FROM Branch b WHERE" +
            ":keyword IS NULL OR " +
            "LOWER(b.name) LIKE LOWER('%' || :keyword || '%') OR " +
            "LOWER(b.address) LIKE LOWER('%' || :keyword || '%')")
    Page<Branch> searchBranches(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
