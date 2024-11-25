package net.blwsmartware.tcourse.controller.web;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.account.UserRequest;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.service.StorageService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class RegisterController {

    UserService userService;

    @GetMapping({"/dang-ky" , "/register"})
    public String register(){
        return "register";
    }
    @PostMapping({"/dang-ky" , "/register"})
    public String registerUser(@Valid UserRequest user, BindingResult result, Model model ,
                               @RequestHeader(value = "Referer", required = false) String referer) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            user.setAvatar("default_image.jpg");
            userService.createUser(user);

        } catch (AppRuntimeException e) {

            model.addAttribute("message", e.getErrorResponse().getMessage());
            return "register";
        }
        model.addAttribute("message", "Đăng ký thành công");

        if (referer != null) {
            return "redirect:" + referer;
        }

        return "login";
    }

    @GetMapping("/confirm")
    public String confirm(){
        return "confirm";
    }
}
