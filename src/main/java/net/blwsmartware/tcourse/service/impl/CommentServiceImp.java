package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.dto.request.comment.CommentRequest;
import net.blwsmartware.tcourse.dto.request.comment.CommentUpdate;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.comment.CommentResponse;
import net.blwsmartware.tcourse.entity.Comment;
import net.blwsmartware.tcourse.entity.Post;
import net.blwsmartware.tcourse.entity.User;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.repository.CommentRepository;
import net.blwsmartware.tcourse.repository.PostRepository;
import net.blwsmartware.tcourse.repository.UserRepository;
import net.blwsmartware.tcourse.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class CommentServiceImp implements CommentService {
    UserRepository userRepository;
    PostRepository postRepository;
    CommentRepository commentRepository;

    @Override
    public Comment createComment(CommentRequest request) {
        Post p = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.POST_NOT_FOUND))  ;
        User created = userRepository.findById(request.getCreatedId())
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USER_NOT_FOUND));
        Comment cmt = Comment.builder()
                .content(request.getContent())
                .created(created)
                .vote(request.getVote())
                .build();
       Comment c=  commentRepository.save(cmt);
       List<Comment> old =p.getComments();
       old.add(c);
       p.setComments(old);
       postRepository.save(p);
         return c;
    }

    @Override
    public DataResponse<CommentResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy) {
        return null;
    }

    @Override
    public CommentResponse getCommentByID(int id) {
        return null;
    }

    @Override
    public CommentResponse updateComment(int id, CommentUpdate update) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
