package net.blwsmartware.tcourse.controller.web;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.comment.CommentRequest;
import net.blwsmartware.tcourse.dto.request.post.PostRequest;
import net.blwsmartware.tcourse.dto.request.post.PostUpdateRequest;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.entity.ImageStorage;
import net.blwsmartware.tcourse.service.CommentService;
import net.blwsmartware.tcourse.service.StorageService;
import net.blwsmartware.tcourse.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class PostController {

    StorageService storageService;
    PostService postService;
    CommentService commentService;

    @GetMapping("/post/update/{id}")
    public String updatePost(Model model , Authentication authentication,
                             @PathVariable long id,
                             @RequestHeader(value = "Referer", required = false) String referer) throws IOException {

        PostResponse post = postService.getPostByID(id);
        model.addAttribute("post", post);
        return "update-step1-post";
    }
    @PostMapping("/post/update/{id}")
    public String update(
            Authentication authentication,
            @Valid @ModelAttribute PostUpdateRequest request,
            BindingResult bindingResult,
            @PathVariable String id,
            Model model ) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Vui lòng kiểm tra thông tin nhập vào.");
            return "create-step1-post";
        }

        PostResponse post = postService.updatePost(Long.parseLong(id),request);
        model.addAttribute("post", post);
        model.addAttribute("success", "Cập nhật thành công.");
        return "update-step2-post";
    }

    @PostMapping("/post")
    public String post(
            Authentication authentication,
           @Valid @ModelAttribute PostRequest postRequest,
           @RequestParam("thumbnail_p") MultipartFile thumbnail,
           @RequestParam("coverPhoto_p") MultipartFile coverPhoto,
           BindingResult bindingResult,
           Model model ) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Vui lòng kiểm tra thông tin nhập vào.");
            return "create-step1-post";
        }

        if(!thumbnail.isEmpty()) {
            ImageStorage avatar1 = storageService.saveToStorage(thumbnail);
            postRequest.setThumbnail(avatar1.getId() + "");
        }

        if(!coverPhoto.isEmpty()) {
            ImageStorage avatar2 = storageService.saveToStorage(coverPhoto);
            postRequest.setCoverPhoto(avatar2.getId() + "");
        }
        if(authentication!=null) {
            String username = authentication.getName();
            postRequest.setCreated_by(username);
        }

        PostResponse post = postService.createPost(postRequest);
        model.addAttribute("post", post);
        return "create-step2-post";
    }
    @PostMapping("/post/comment/create")
    public String createComment(@ModelAttribute CommentRequest commentRequest, Model model ,
                                @RequestHeader(value = "Referer", required = false) String referer) {
        log.info("Comment :{}",commentRequest);
        commentService.createComment(commentRequest);
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:/home" ;
    }


}
