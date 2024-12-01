package net.blwsmartware.tcourse.controller.web;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.constant.PredefinedRole;
import net.blwsmartware.tcourse.dto.request.account.RoleOfUpdate;
import net.blwsmartware.tcourse.dto.request.account.UserRequest;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;
import net.blwsmartware.tcourse.entity.User;
import net.blwsmartware.tcourse.service.RoleService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserController {
    RoleService roleService;
    UserService userService;

    @PostMapping("/me/info")
    public String post(@Valid UserRequest user, BindingResult result, Model model ,
                       @RequestHeader(value = "Referer", required = false) String referer) {

        model.addAttribute("message", "Đăng ký thành công");

        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:profile/setting";
    }
    @GetMapping("/users/request/{roleName}" )
    public String request( Model model , Authentication authentication,
                                     @PathVariable String roleName,
                                     @RequestHeader(value = "Referer", required = false) String referer) {

        if(roleName.equals(PredefinedRole.TEACHER_ROLE)) {
            String username = authentication.getName();
            RoleOfUpdate roles = new RoleOfUpdate();
            roles.setRoleIds(Set.of(roleService.getRoleByName(roleName).getId()));
            UserResponse u = userService.getUserByUsername(username);
            boolean isTeacher = u.getRoles().stream()
                    .anyMatch(role -> role.getName().equals(PredefinedRole.TEACHER_ROLE));
            if(!isTeacher) userService.updateRoleOfUser(u.getId(), roles);
            if (referer != null) {
                return "redirect:" + referer+"?result=oke";
            }
        }


        return "redirect:profile/setting";
    }
}
