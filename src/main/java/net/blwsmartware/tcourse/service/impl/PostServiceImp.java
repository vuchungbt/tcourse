package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.dto.request.post.PostRequest;
import net.blwsmartware.tcourse.dto.request.post.PostUpdate;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.entity.Post;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.mapper.PostMapper;
import net.blwsmartware.tcourse.repository.PostRepository;
import net.blwsmartware.tcourse.service.PostService;
import net.blwsmartware.tcourse.util.DataResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImp implements PostService {

    PostMapper postMapper;
    PostRepository postRepository;

    @Override
    public PostResponse createPost(PostRequest request) {
        Post post = postMapper.toPost(request);
         return postMapper.toPostResponse(postRepository.save(post));
    }

    @Override
    public PostResponse getPostByID(long id) {
        return postMapper.toPostResponse(postRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.POST_NOT_FOUND)));
    }

    @Override
    public PostResponse updatePost(long id, PostUpdate update) {
        Post old = postRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.POST_NOT_FOUND));
        postMapper.updatePost(update,old);
        return postMapper.toPostResponse(postRepository.save(old));
    }

    @Override
    public DataResponse<PostResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Post> pageOfPost = postRepository.findAll(pageable);
        List<Post> userList = pageOfPost.getContent();
        List<PostResponse> postResponses = userList.stream().map(postMapper::toPostResponse).toList();
        return DataResponseUtils.convertPageInfo(pageOfPost,postResponses);
    }


    @Override
    public void delete(long id) {
        postRepository.deleteById(id);
    }
}
