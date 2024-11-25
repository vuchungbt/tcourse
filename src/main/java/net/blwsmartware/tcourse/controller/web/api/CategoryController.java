package net.blwsmartware.tcourse.controller.web.api;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.response.post.CategoryResponse;
import net.blwsmartware.tcourse.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CategoryController {

    CategoryService categoryService;

    @GetMapping("/categories/all" )
    public List<CategoryResponse> getAll(){
        return categoryService.getAll();
    }
}
