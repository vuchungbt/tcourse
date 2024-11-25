package net.blwsmartware.tcourse.controller.web;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.service.StorageService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CourseController {

    UserService userService;
    StorageService storageService;

    @GetMapping("/api/course/upload-videos")
    public String upload(){
        return "9uploadvideo";
    }
}
