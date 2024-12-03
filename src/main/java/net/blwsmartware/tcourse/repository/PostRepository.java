package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCreatedId(Long createdId);
    Page<Post> findByCreatedId(Long createdId,Pageable pageable);
    Page<Post> findByCategories(Category categoryId, Pageable pageable);
    //@Query("SELECT DISTINCT d.item FROM InvoiceDetail d JOIN d.invoice i WHERE i.created.id = :userId")
    @Query("SELECT DISTINCT p FROM Post p JOIN InvoiceDetail id ON p.id = id.item.id JOIN Invoice i ON id.invoice.id = i.id WHERE i.created.id = :userId")
    Page<Post> findAllPostsByUserId(@Param("userId") Long userId ,Pageable pageable);
}
