package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.dto.request.comment.CommentRequest;
import net.blwsmartware.tcourse.dto.request.comment.CommentUpdate;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.comment.CommentResponse;
import net.blwsmartware.tcourse.service.CommentService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImp implements CommentService {
    @Override
    public CommentResponse createComment(CommentRequest request) {
         return null;
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
