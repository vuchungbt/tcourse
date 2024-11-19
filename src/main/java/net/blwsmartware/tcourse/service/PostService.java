package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.dto.request.post.PostRequest;
import net.blwsmartware.tcourse.dto.request.post.PostUpdate;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;

public interface PostService {
    PostResponse createPost(PostRequest request);
    PostResponse getPostByID(long id);
    PostResponse updatePost(long id, PostUpdate update);
    DataResponse<PostResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy);
    void delete(long id);
}
