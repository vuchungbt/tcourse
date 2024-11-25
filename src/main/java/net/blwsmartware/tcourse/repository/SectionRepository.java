package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Section;
import net.blwsmartware.tcourse.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
