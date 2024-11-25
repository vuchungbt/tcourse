package net.blwsmartware.tcourse.controller.web;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.constant.PagePrepare;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.service.PostService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequestMapping("/profile")
public class ProfileController {

    PostService postService;
    UserService userService;

    @GetMapping
    public String profile(Authentication authentication, Model model,
                          @RequestParam(value = "number",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                          @RequestParam(value = "page", defaultValue = "12", required = false) Integer pageSize,
                          @RequestParam(value = "c", defaultValue = "0", required = false) long category,
                          @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy,
                          @RequestHeader(value = "Referer", required = false) String referer){

            String username = authentication.getName();
            model.addAttribute("username", username);

            model.addAttribute("user", userService.getUserByUsername(username));
            DataResponse<PostResponse> list = postService.getPostByCreated(userService.getUserByUsername(username).getId() , pageNumber,  pageSize, sortBy );
            list.setName("Khóa học đã đăng");
            model.addAttribute("list_post_all",  list);

        return "profile";
    }

    @GetMapping("/post")
    public String post(Authentication authentication, Model model){
        if(authentication!=null) {

            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        return "create-step1-post";
    }
}
