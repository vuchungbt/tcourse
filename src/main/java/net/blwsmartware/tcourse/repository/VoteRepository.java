package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Post;
import net.blwsmartware.tcourse.entity.User;
import net.blwsmartware.tcourse.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPostIdAndUser(long postId, User user);
}
