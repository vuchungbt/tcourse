package net.blwsmartware.tcourse.controller.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.constant.PagePrepare;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequestMapping("/admin")
public class AdminDashboardController {

    UserService userService;

    @GetMapping({"/dashboard"})
    public String home(Authentication authentication, Model model,
                       @RequestParam(value = "pageNumber",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PagePrepare.PAGE_SIZE, required = false) Integer pageSize,
                       @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy){
        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        model.addAttribute("users", userService.getAll());

        return "admin/dashboard";
    }
    @GetMapping({"/info"})
    public String infoUpdate(Authentication authentication, Model model ){
        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }

        return "admin/update-myprofile";
    }
}
