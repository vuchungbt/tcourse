package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
