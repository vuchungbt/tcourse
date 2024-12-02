package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.dto.request.post.PostRequest;
import net.blwsmartware.tcourse.dto.request.post.PostUpdate;
import net.blwsmartware.tcourse.dto.request.post.PostUpdateSection;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest request);
    PostResponse getPostByID(long id);
    List<PostResponse> getPostByCreated(long id);
    DataResponse<PostResponse> getPostByCreated(long id ,Integer pageNumber, Integer pageSize, String sortBy);
    DataResponse<PostResponse>  getPostByCategory(long id ,Integer pageNumber, Integer pageSize, String sortBy);
    PostResponse updatePost(long id, PostUpdate update);
    PostResponse updatePostSection(long id, PostUpdateSection update);
    DataResponse<PostResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy);
    DataResponse<PostResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy,String name,String filter);
    List<PostResponse> getAll();
    void delete(long id);
     List<PostResponse> findAllPostsByUserId(long id);
}
