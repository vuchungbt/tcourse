package net.blwsmartware.tcourse.controller.web;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.post.PostUpdateSection;
import net.blwsmartware.tcourse.dto.request.post.SectionForm;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.entity.Post;
import net.blwsmartware.tcourse.entity.Section;
import net.blwsmartware.tcourse.entity.Tag;
import net.blwsmartware.tcourse.mapper.CategoryMapper;
import net.blwsmartware.tcourse.service.CategoryService;
import net.blwsmartware.tcourse.service.PostService;
import net.blwsmartware.tcourse.service.SectionService;
import net.blwsmartware.tcourse.service.TagService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class SectionController {

    PostService postService;
    TagService tagService ;
    SectionService sectionService ;
    CategoryService categoryService ;
    CategoryMapper categoryMapper ;

    @PostMapping("/section")
    public String addSection(@ModelAttribute SectionForm form ,
                             Authentication authentication, Model model,
                             @RequestParam("tags") String tags ,
                             @RequestParam("category") long category,
                             @RequestParam("post_id") long post_id
                             ) {

        Set<Tag> tagSet= new java.util.HashSet<>( );
        Set<Section> sectionSet = new java.util.HashSet<>( );
        Set<Category> categories = new java.util.HashSet<>( );

        tags = tags.replace(" ",",");
        String[] tagArray = tags.split(",");

        for (String tag : tagArray) {
            System.out.println("---------Thẻ tag: " + tag);
            tagSet.add(tagService.create(tag));
        }
        if(form!=null && !form.getComponents().isEmpty()) {
            form.getComponents().forEach(component -> {
                System.out.println("component:"+ component);
                System.out.println("component name:"+ component.getName());
                sectionSet.add(sectionService.create(component));
            });
        }
        categories.add(categoryMapper.toCategory(categoryService.getCategoryByID(category)));
        PostUpdateSection postUpdateSection = PostUpdateSection.builder()
                .sections(sectionSet)
                .categories(categories)
                .tags(tagSet)
                .build();

        PostResponse postResponse =  postService.updatePostSection(post_id,postUpdateSection);
        log.info("postResponse: {}",postResponse);
        model.addAttribute("post",  postResponse);
        return "uploadvideo";
    }
}
