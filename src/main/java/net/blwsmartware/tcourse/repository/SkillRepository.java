package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Post;
import net.blwsmartware.tcourse.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
