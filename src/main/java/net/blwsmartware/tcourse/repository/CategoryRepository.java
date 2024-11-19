package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
