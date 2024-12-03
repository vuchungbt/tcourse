package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.dto.request.post.PostRequest;
import net.blwsmartware.tcourse.dto.request.post.PostUpdate;
import net.blwsmartware.tcourse.dto.request.post.PostUpdateSection;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.entity.*;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.mapper.PostMapper;
import net.blwsmartware.tcourse.repository.*;
import net.blwsmartware.tcourse.service.PostService;
import net.blwsmartware.tcourse.util.DataResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImp implements PostService {

    PostMapper postMapper;
    PostRepository postRepository;
    SkillRepository skillRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    DiscountRepository discountRepository;

    @Override
    public PostResponse createPost(PostRequest request) {
        Post post = postMapper.toPost(request);

        Set<Skill> skills = request.getSkills().stream()
                .map(name -> skillRepository.save(Skill.builder().name(name).build()))
                .collect(Collectors.toSet());
        User created = userRepository.findByUsername(request.getCreated_by())
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.USERNAME_NOT_NULL));
        post.setCreated(created);
        post.setSkills(skills);
        Discount discount = Discount.builder()
                .def(true)
                .status(true)
                .percent(request.getDiscount())
                .build();
        discount= discountRepository.save(discount);
        post.setDiscounts(Set.of(discount));
        return postMapper.toPostResponse(postRepository.save(post));
    }

    @Override
    public PostResponse getPostByID(long id) {
        return postMapper.toPostResponse(postRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.POST_NOT_FOUND)));
    }

    @Override
    public List<PostResponse> getPostByCreated(long id) {
        List<Post> list = postRepository.findByCreatedId(id);
        return list.stream().map(postMapper::toPostResponse).toList();
    }

    @Override
    public DataResponse<PostResponse> getPostByCreated(long id, Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Post> pageOfPost = postRepository.findByCreatedId(id,pageable);
        List<Post> userList = pageOfPost.getContent();
        System.out.printf("userList:"+userList.size());
        List<PostResponse> postResponses = userList.stream().map(postMapper::toPostResponse).toList();
        return DataResponseUtils.convertPageInfo(pageOfPost,postResponses);
    }

    @Override
    public DataResponse<PostResponse> getPostByCategory(long id ,Integer pageNumber, Integer pageSize, String sortBy) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.CATEGORY_NOT_FOUND));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Post> pageOfPost = postRepository.findByCategories(category,pageable);
        List<Post> userList = pageOfPost.getContent();
        List<PostResponse> postResponses = userList.stream().map(postMapper::toPostResponse).toList();
        DataResponse<PostResponse> list = DataResponseUtils.convertPageInfo(pageOfPost,postResponses);
        list.setName(category.getName());
        return list;
    }

    @Override
    public PostResponse updatePost(long id, PostUpdate update) {
        Post old = postRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.POST_NOT_FOUND));
        postMapper.updatePost(update,old);
        return postMapper.toPostResponse(postRepository.save(old));
    }

    @Override
    public PostResponse updatePostSection(long id, PostUpdateSection update) {
        Post old = postRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.POST_NOT_FOUND));
        postMapper.updatePostSection(update,old);
        postRepository.save(old);
        return postMapper.toPostResponse(old);
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
    public DataResponse<PostResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String name, String filter) {
        DataResponse<PostResponse> list = this.getAll(pageNumber, pageSize, sortBy);
        list.setName(name);
        return null;
    }

    @Override
    public List<PostResponse> getAll() {
       return  postRepository.findAll().stream().map(postMapper::toPostResponse).toList();
    }


    @Override
    public void delete(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public DataResponse<PostResponse> findAllPostsByUserId(long id ,Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Post> pageOfPost = postRepository.findAllPostsByUserId(id,pageable);
        List<Post> userList = pageOfPost.getContent();
        List<PostResponse> postResponses = userList.stream().map(postMapper::toPostResponse).toList();
        return DataResponseUtils.convertPageInfo(pageOfPost,postResponses);
    }
}
