package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.dto.request.comment.CommentRequest;
import net.blwsmartware.tcourse.dto.request.comment.CommentUpdate;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.comment.CommentResponse;

public interface CommentService {
    CommentResponse createComment(CommentRequest request);
    DataResponse<CommentResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy);
    CommentResponse getCommentByID(int id);
    CommentResponse updateComment(int id, CommentUpdate update);
    void delete(int id);
}
