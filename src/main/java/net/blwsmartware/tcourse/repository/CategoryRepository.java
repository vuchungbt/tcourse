package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByDescription(String description);
}
