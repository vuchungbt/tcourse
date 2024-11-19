package net.blwsmartware.tcourse.mapper;

import net.blwsmartware.tcourse.dto.request.comment.CommentRequest;
import net.blwsmartware.tcourse.dto.request.comment.CommentUpdate;
import net.blwsmartware.tcourse.dto.response.comment.CommentResponse;
import net.blwsmartware.tcourse.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CommentRequest request);
    void updateComment(CommentUpdate commentUpdate, @MappingTarget Comment p);
    CommentResponse toCommentResponse(Comment comment);
}
