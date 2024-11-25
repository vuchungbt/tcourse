package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCreatedId(Long createdId);
    Page<Post> findByCreatedId(Long createdId,Pageable pageable);
    Page<Post> findByCategories(Category categoryId, Pageable pageable);
}
