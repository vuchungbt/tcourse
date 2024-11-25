package net.blwsmartware.tcourse.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.constant.PagePrepare;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.service.CategoryService;
import net.blwsmartware.tcourse.service.PostService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class HomeController {

    CategoryService categoryService;
    PostService postService;
    UserService userService;

    @GetMapping("/home/p/{id}" )
    public String course(Authentication authentication, Model model,
                         @PathVariable long id,
                          @RequestHeader(value = "Referer", required = false) String referer ){



        model.addAttribute("list_category_all", categoryService.getAll( 0,15, PagePrepare.SORT_BY) );

        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        model.addAttribute("post", postService.getPostByID(id));

        return "/chitiet-khoahoc";
    }

    @GetMapping("/home/all")
    public String homeAll(Authentication authentication, Model model,
                       @RequestParam(value = "number",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                       @RequestParam(value = "page", defaultValue = "12", required = false) Integer pageSize,
                       @RequestParam(value = "c", defaultValue = "0", required = false) long category,
                       @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy,
                       @RequestHeader(value = "Referer", required = false) String referer){

        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        model.addAttribute("list_category_all", categoryService.getAll( 0,15, PagePrepare.SORT_BY) );

        DataResponse<PostResponse> response ;

        if(category==0) {
            response =postService.getAll(pageNumber, pageSize,sortBy);
            response.setName("Tất cả");
        } else {
            response =postService.getPostByCategory(category,pageNumber, pageSize,sortBy);

        }

        model.addAttribute("list_post_all",response    );

        return "/index-all";
    }
    @GetMapping({"/trang-chu","/" ,"/home"})
    public String home(Authentication authentication, Model model,
                       @RequestParam(value = "pageNumber",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                       @RequestParam(value = "page", defaultValue = PagePrepare.PAGE_SIZE, required = false) Integer pageSize,
                       @RequestParam(value = "c", defaultValue = "0", required = false) long category,
                       @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy,
                       @RequestHeader(value = "Referer", required = false) String referer){

        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        model.addAttribute("list_category_all", categoryService.getAll( 0,15, PagePrepare.SORT_BY) );

        DataResponse<PostResponse> response ;

        if(category==0) {
            response =postService.getAll(pageNumber, pageSize,sortBy);
            response.setName("Tất cả");
        } else {
            response =postService.getPostByCategory(category,pageNumber, pageSize,sortBy);

        }

        model.addAttribute("list_post_all",response    );

        return "/index";
    }
}
