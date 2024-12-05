package net.blwsmartware.tcourse.controller.web;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.EmailRequest;
import net.blwsmartware.tcourse.dto.request.account.EmailUserUpdate;
import net.blwsmartware.tcourse.dto.request.account.UserRequest;
import net.blwsmartware.tcourse.dto.request.account.UserUpdate;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.service.EmailService;
import net.blwsmartware.tcourse.service.StorageService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class RegisterController {

    UserService userService;
    EmailService emailService;

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

//        if (referer != null) {
//            return "redirect:" + referer;
//        }

        return "login";
    }

    @GetMapping("/confirm")
    public String confirm(){
        return "confirm";
    }
    @GetMapping("/forgot")
    public String forgot(){
        return "forgot";
    }
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // Các ký tự hợp lệ
        Random random = new Random();
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }

        return result.toString();
    }
    @PostMapping("/forgot/email")
    public String forgotPw(@Valid  EmailUserUpdate email, BindingResult result, Model model ,
                                      @RequestHeader(value = "Referer", required = false) String referer) {
            String p = generateRandomString(8);
            UserResponse userResponse = userService.getUserByEmail(email.getEmail());
            userService.updatePassNew(userResponse.getId(), p);

            EmailRequest e = EmailRequest.builder()
                    .to(email.getEmail())
                    .content("Vui lòng đổi mật khẩu")
                    .name(p)
                    .build();
            emailService.sendEmail(e);

            if (referer != null) {
                return "redirect:" + referer+"?success";
            } return "redirect:/forgot?error";
    }
}
