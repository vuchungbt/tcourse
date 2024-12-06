package net.blwsmartware.tcourse.controller.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.constant.PagePrepare;
import net.blwsmartware.tcourse.service.PostService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequestMapping("/admin")
public class AdminAccountController {

    UserService userService;
    PostService postService;


    @GetMapping({"/account"})
    public String home(Model model,
                       @RequestParam(value = "pageNumber",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PagePrepare.PAGE_SIZE, required = false) Integer pageSize,
                       @RequestParam(value = "q", defaultValue = PagePrepare.CATEGORY, required = false) List<String> query,
                       @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy){

        model.addAttribute("users",
                userService.getAllByRoleName(-1,10,PagePrepare.SORT_BY,query));

        return "admin/account-dashboard";
    }
    // Xử lý xóa user
    @PostMapping("/account/delete")
    public String deleteUser(@RequestParam Long userId, RedirectAttributes attributes ,
                             @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            userService.deleteUser(userId);
            attributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
        }
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:admin/account?q=ALL";
    }

    @PostMapping("/post/delete")
    public String deletePost(@RequestParam Long postId, RedirectAttributes attributes ,
                             @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            postService.deletePost(postId);
            attributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
        }
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:admin/course?c=ALL";
    }
}
