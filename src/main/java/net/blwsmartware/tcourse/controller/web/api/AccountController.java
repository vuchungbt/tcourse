package net.blwsmartware.tcourse.controller.web.api;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.account.UserRequest;
import net.blwsmartware.tcourse.dto.request.account.UserUpdate;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class AccountController {

    UserService userService;

    @GetMapping("/users/{id}" )
    public UserResponse info(@PathVariable long id){
        return userService.getUserByID(id);
    }

    @PutMapping("/users/update/{id}" )
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdate user, BindingResult result, Model model ,
                         @PathVariable long id,
                         @RequestHeader(value = "Referer", required = false) String referer) {
        log.info("ID: {}",id);
        log.info("user: {}",user);
        try {
            userService.updateUser(id, user);

            return ResponseEntity.ok()
                    .body(Map.of(
                            "success", true,
                            "message", "User updated successfully"
                    ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }
}
