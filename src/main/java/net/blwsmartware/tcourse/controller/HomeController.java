package net.blwsmartware.tcourse.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.constant.PagePrepare;
import net.blwsmartware.tcourse.constant.PredefinedRole;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;
import net.blwsmartware.tcourse.entity.AppSetting;
import net.blwsmartware.tcourse.entity.Discount;
import net.blwsmartware.tcourse.entity.Vote;
import net.blwsmartware.tcourse.service.AppSettingService;
import net.blwsmartware.tcourse.service.CategoryService;
import net.blwsmartware.tcourse.service.PostService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class HomeController {

    CategoryService categoryService;
    PostService postService;
    UserService userService;
    AppSettingService appSettingService;

    @GetMapping("/home/about/{v}")
    public String about(@PathVariable int v , Model model){
        AppSetting appSetting = appSettingService.getByID(v);
        model.addAttribute("about", appSetting);
        return "about";
    }
    @GetMapping("/stream/view/{id}")
    public String view(@PathVariable String id , Model model){
        model.addAttribute("id", id);
        return "vd";
    }

    @GetMapping("/home/p/{id}" )
    public String course(Authentication authentication, Model model,
                         @PathVariable long id,
                          @RequestHeader(value = "Referer", required = false) String referer ){

        model.addAttribute("list_category_all", categoryService.getAll( 0,15, PagePrepare.SORT_BY) );

        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        PostResponse p =postService.getPostByID(id);
        int totalItems = p.getSections().stream()
                .mapToInt(section -> section.getItems().size())
                .sum();
        Optional<Discount> defaultDiscount = p.getDiscounts()
                .stream()
                .filter(Discount::isDef)
                .findFirst();

        if (defaultDiscount.isPresent()) {
            Discount discount = defaultDiscount.get();
            p.setDiscountPercent(discount.getPercent());
            p.setFinalPrice(p.getPrice() - p.getPrice()* discount.getPercent()/100) ;
        } else {
            p.setDiscountPercent(0);
            p.setFinalPrice(p.getPrice());
        }
        double total = p.getVotes().stream().mapToInt(Vote::getStars).sum();
        double avg = total / p.getVotes().size();
        avg = Math.round(avg * 10) / 10.0;
        p.setAvgVote(avg);

        model.addAttribute("post", p);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("cre", userService.getUserByUsername(p.getCreated_by()));

        log.info("=================={}",p);
        return "chitiet-khoahoc";
    }
    @GetMapping("/home/all/search")
    public String search(Authentication authentication, Model model,
                          @RequestParam(value = "number",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                          @RequestParam(value = "page", defaultValue = "12", required = false) Integer pageSize,
                          @RequestParam(value = "v", defaultValue = "", required = false) String key,
                          @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy,
                          @RequestHeader(value = "Referer", required = false) String referer){

        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        model.addAttribute("list_category_all", categoryService.getAll( 0,15, PagePrepare.SORT_BY) );

        DataResponse<PostResponse> response ;

        if(!key.isEmpty()) {
            response =postService.search(key,pageNumber, pageSize,sortBy);
            response.setName(key);
            response.getContent().forEach(p -> {
                Optional<Discount> defaultDiscount = p.getDiscounts()
                        .stream()
                        .filter(Discount::isDef)
                        .findFirst();

                if (defaultDiscount.isPresent()) {
                    Discount discount = defaultDiscount.get();
                    p.setDiscountPercent(discount.getPercent());
                    p.setFinalPrice(p.getPrice() - p.getPrice()* discount.getPercent()/100) ;
                } else {
                    p.setDiscountPercent(0);
                    p.setFinalPrice(p.getPrice());
                }

                double total = p.getVotes().stream().mapToInt(Vote::getStars).sum();
                double avg = total / p.getVotes().size();
                avg = Math.round(avg * 10) / 10.0;
                p.setAvgVote(avg);
            });
        } else {
            response =postService.getAll(pageNumber, pageSize,sortBy);
            response.getContent().forEach(p -> {
                Optional<Discount> defaultDiscount = p.getDiscounts()
                        .stream()
                        .filter(Discount::isDef)
                        .findFirst();

                if (defaultDiscount.isPresent()) {
                    Discount discount = defaultDiscount.get();
                    p.setDiscountPercent(discount.getPercent());
                    p.setFinalPrice(p.getPrice() - p.getPrice()* discount.getPercent()/100) ;
                } else {
                    p.setDiscountPercent(0);
                    p.setFinalPrice(p.getPrice());
                }

                double total = p.getVotes().stream().mapToInt(Vote::getStars).sum();
                double avg = total / p.getVotes().size();
                avg = Math.round(avg * 10) / 10.0;
                p.setAvgVote(avg);
            });
        }

        model.addAttribute("list_post_all",response    );

        return "index-search";
    }

    @GetMapping("/home/all")
    public String homeAll(Authentication authentication, Model model,
                       @RequestParam(value = "number",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                       @RequestParam(value = "page", defaultValue = "12", required = false) Integer pageSize,
                       @RequestParam(value = "c", defaultValue = "0", required = false) long category,
                       @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy,
                       @RequestHeader(value = "Referer", required = false) String referer){

        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        model.addAttribute("list_category_all", categoryService.getAll( 0,15, PagePrepare.SORT_BY) );

        DataResponse<PostResponse> response ;

        if(category==0) {
            response =postService.getAll(pageNumber, pageSize,sortBy);
            response.setName("Tất cả");
            response.getContent().forEach(p -> {
                Optional<Discount> defaultDiscount = p.getDiscounts()
                        .stream()
                        .filter(Discount::isDef)
                        .findFirst();

                if (defaultDiscount.isPresent()) {
                    Discount discount = defaultDiscount.get();
                    p.setDiscountPercent(discount.getPercent());
                    p.setFinalPrice(p.getPrice() - p.getPrice()* discount.getPercent()/100) ;
                } else {
                    p.setDiscountPercent(0);
                    p.setFinalPrice(p.getPrice());
                }

                double total = p.getVotes().stream().mapToInt(Vote::getStars).sum();
                double avg = total / p.getVotes().size();
                avg = Math.round(avg * 10) / 10.0;
                p.setAvgVote(avg);
            });
        } else {
            response =postService.getPostByCategory(category,pageNumber, pageSize,sortBy);
            response.getContent().forEach(p -> {
                Optional<Discount> defaultDiscount = p.getDiscounts()
                        .stream()
                        .filter(Discount::isDef)
                        .findFirst();

                if (defaultDiscount.isPresent()) {
                    Discount discount = defaultDiscount.get();
                    p.setDiscountPercent(discount.getPercent());
                    p.setFinalPrice(p.getPrice() - p.getPrice()* discount.getPercent()/100) ;
                } else {
                    p.setDiscountPercent(0);
                    p.setFinalPrice(p.getPrice());
                }

                double total = p.getVotes().stream().mapToInt(Vote::getStars).sum();
                double avg = total / p.getVotes().size();
                avg = Math.round(avg * 10) / 10.0;
                p.setAvgVote(avg);
            });
        }

        model.addAttribute("list_post_all",response    );

        return "index-all";
    }
    @GetMapping({"/trang-chu","/" ,"/home"})
    public String home(Authentication authentication, Model model,
                       @RequestParam(value = "pageNumber",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                       @RequestParam(value = "page", defaultValue = PagePrepare.PAGE_SIZE, required = false) Integer pageSize,
                       @RequestParam(value = "c", defaultValue = "0", required = false) long category,
                       @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy,
                       @RequestHeader(value = "Referer", required = false) String referer){

        if(authentication!=null) {
            String username = authentication.getName();
            log.info("username-{}",username);
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        model.addAttribute("list_category_all", categoryService.getAll( pageNumber,pageSize, PagePrepare.SORT_BY) );

        DataResponse<PostResponse> response_post ;
        DataResponse<UserResponse> response_teacher = userService.getAllPageByRoleName( pageNumber,pageSize, PagePrepare.SORT_BY , List.of(PredefinedRole.TEACHER_ROLE)) ;
        if(category==0) {
            response_post =postService.getAll(pageNumber, pageSize,sortBy);
            response_post.setName("Tất cả");
        } else {
            response_post =postService.getPostByCategory(category,pageNumber, pageSize,sortBy);

        }
        response_post.getContent().forEach(p -> {
            Optional<Discount> defaultDiscount = p.getDiscounts()
                    .stream()
                    .filter(Discount::isDef)
                    .findFirst();

            if (defaultDiscount.isPresent()) {
                Discount discount = defaultDiscount.get();
                p.setDiscountPercent(discount.getPercent());
                p.setFinalPrice(p.getPrice() - p.getPrice()* discount.getPercent()/100) ;
            } else {
                p.setDiscountPercent(0);
                p.setFinalPrice(p.getPrice());
            }

            double total = p.getVotes().stream().mapToInt(Vote::getStars).sum();
            double avg = total / p.getVotes().size();
            avg = Math.round(avg * 10) / 10.0;
            p.setAvgVote(avg);
        });

        model.addAttribute("list_post_all",response_post    );
        response_teacher.setName("Top Giảng viên");
        model.addAttribute("list_teacher",response_teacher    );

        return "index";
    }
    @GetMapping("/home/payment")
    public String homePayment(Authentication authentication, Model model ){
        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        return "payment";
    }
}
