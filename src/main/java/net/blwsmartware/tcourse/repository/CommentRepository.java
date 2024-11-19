package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
