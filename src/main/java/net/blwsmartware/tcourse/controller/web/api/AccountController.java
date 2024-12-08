package net.blwsmartware.tcourse.controller.web.api;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.account.*;
import net.blwsmartware.tcourse.dto.request.post.CategoryUpdate;
import net.blwsmartware.tcourse.dto.request.post.PostRequest;
import net.blwsmartware.tcourse.dto.response.post.CategoryResponse;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;
import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.service.CategoryService;
import net.blwsmartware.tcourse.service.RoleService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class AccountController {

    UserService userService;
    CategoryService categoryService;

    @PostMapping("/users/r/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserRoleRequest request) {
        try {
            userService.updateRoleAndActive(id, request);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating user");
        }
    }


    @GetMapping("/users/{id}" )
    public UserResponse info(@PathVariable long id){
        return userService.getUserByID(id);
    }
    @GetMapping("/category/{id}" )
    public CategoryResponse category(@PathVariable long id){
        return categoryService.getCategoryByID(id);
    }
    @PutMapping("/category/update/{id}" )
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryUpdate cate, BindingResult result, Model model ,
                                            @PathVariable long id,
                                            @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            categoryService.updateCategory(id, cate);

            return ResponseEntity.ok()
                    .body(Map.of(
                            "success", true,
                            "message", "Cate updated successfully"
                    ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }

    @PostMapping("/users/update/{id}" )
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdate user, BindingResult result, Model model ,
                         @PathVariable long id,
                         @RequestHeader(value = "Referer", required = false) String referer) {
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


    @PostMapping("/users/account/{id}" )
    public ResponseEntity<?> account(@Valid @RequestBody UsernameOrEmailUserUpdate user,
                                     BindingResult result, Model model ,
                                     @PathVariable long id,
                                     @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            userService.updateUsernameEmail(id, user);

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
    @PostMapping("/users/pwd/{id}" )
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordUserUpdate user,
                                     BindingResult result, Model model ,
                                     @PathVariable long id,
                                     @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            System.out.println("Update pwd:" + user.getPassword());
            userService.updatePassword(id, user);

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
    @PostMapping("/users/photo/{id}")
    public ResponseEntity<?>  photo(
            Authentication authentication,
            @Valid @ModelAttribute PhotoUserUpdate postRequest,
            @PathVariable long id,
            BindingResult bindingResult,
            Model model ) throws IOException {
        try {
            userService.updatePhoto(id, postRequest);

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
