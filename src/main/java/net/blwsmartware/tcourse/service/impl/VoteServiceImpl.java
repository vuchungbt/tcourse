package net.blwsmartware.tcourse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.entity.Post;
import net.blwsmartware.tcourse.entity.Tag;
import net.blwsmartware.tcourse.entity.User;
import net.blwsmartware.tcourse.entity.Vote;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.repository.PostRepository;
import net.blwsmartware.tcourse.repository.TagRepository;
import net.blwsmartware.tcourse.repository.UserRepository;
import net.blwsmartware.tcourse.repository.VoteRepository;
import net.blwsmartware.tcourse.service.TagService;
import net.blwsmartware.tcourse.service.VoteService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VoteServiceImpl implements VoteService {

    VoteRepository voteRepository;
    PostRepository postRepository;
    UserRepository userRepository;

    @Override
    public Vote addVote(Long postId, Long userId, int stars) {
        // Kiểm tra sao hợp lệ
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Sao phải nằm trong khoảng từ 1 đến 5");
        }
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Bài viết không tồn tại"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Người dùng không tồn tại"));

        Optional<Vote> existingVote = voteRepository.findByPostIdAndUser(postId, user);
        if (existingVote.isPresent()) {
            throw new IllegalStateException("Bạn đã vote bài viết này rồi");
        }
        Vote vote = Vote.builder()
                .stars(stars)
                .postId(postId)
                .user(user)
                .build() ;

        vote = voteRepository.save(vote);
        Set<Vote> v = post.getVotes() ;
        v.add(vote);
        post.setVotes(v);
        log.info("Vote : {}", vote);
        log.info("Set<Vote> : {}", v);
        log.info("post : {}", post);
        postRepository.save(post);
        return vote;
    }

    @Override
    public Vote getByID(long id) {
        return voteRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.POST_NOT_FOUND));
    }

    @Override
    public void delete(long id) {
        voteRepository.deleteById(id);
    }
}
