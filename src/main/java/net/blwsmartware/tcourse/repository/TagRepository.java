package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
