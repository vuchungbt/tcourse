package net.blwsmartware.tcourse.controller.web;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.post.PostUpdateSection;
import net.blwsmartware.tcourse.dto.request.post.SectionForm;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.entity.Section;
import net.blwsmartware.tcourse.mapper.CategoryMapper;
import net.blwsmartware.tcourse.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class SectionController {

    PostService postService;
    UserService userService;
    SectionService sectionService ;
    CategoryService categoryService ;
    CategoryMapper categoryMapper ;

    @PostMapping("/section")
    public String addSection(@ModelAttribute SectionForm form ,
                             Authentication authentication, Model model,
                             @RequestParam("category") long category,
                             @RequestParam("post_id") long post_id
                             ) {


        Set<Section> sectionSet = new java.util.HashSet<>( );
        Set<Category> categories = new java.util.HashSet<>( );

        if(form!=null && !form.getComponents().isEmpty()) {
            form.getComponents().forEach(component -> {
                sectionSet.add(sectionService.create(component));
            });
        }
        categories.add(categoryMapper.toCategory(categoryService.getCategoryByID(category)));
        PostUpdateSection postUpdateSection = PostUpdateSection.builder()
                .sections(sectionSet)
                .categories(categories)
                .build();

        PostResponse postResponse =  postService.updatePostSection(post_id,postUpdateSection);
        log.info("postResponse: {}",postResponse);
        model.addAttribute("post",  postResponse);
        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }

        return "uploadvideo";
    }
}
