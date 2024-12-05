package net.blwsmartware.tcourse.controller.web;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.constant.PagePrepare;
import net.blwsmartware.tcourse.constant.PredefinedRole;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.post.PostResponse;
import net.blwsmartware.tcourse.dto.response.user.UserResponse;
import net.blwsmartware.tcourse.entity.Card;
import net.blwsmartware.tcourse.entity.Discount;
import net.blwsmartware.tcourse.entity.Vote;
import net.blwsmartware.tcourse.service.InvoiceService;
import net.blwsmartware.tcourse.service.PostService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequestMapping("/profile")
public class ProfileController {

    PostService postService;
    UserService userService;

    @GetMapping
    public String profile(Authentication authentication, Model model,
                          @RequestParam(value = "number",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                          @RequestParam(value = "page", defaultValue = "12", required = false) Integer pageSize,
                          @RequestParam(value = "c", defaultValue = "0", required = false) long category,
                          @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy,
                          @RequestParam(value = "v", defaultValue = "", required = false) String pn,
                          @RequestHeader(value = "Referer", required = false) String referer){


            String username = authentication.getName();
            if(!pn.isEmpty()) {
                username=pn;
            }
            long id =userService.getUserByUsername(username).getId();

            model.addAttribute("username", username);

            model.addAttribute("user", userService.getUserByUsername(username));
            DataResponse<PostResponse> list = postService.getPostByCreated(id , pageNumber,  pageSize, sortBy );
            list.setName("Khóa học đã đăng");
            List<PostResponse> buy = postService.findAllPostsByUserId(id );
            list.getContent().forEach(p -> {
                Optional<Discount> defaultDiscount = p.getDiscounts()
                        .stream()
                        .filter(Discount::isDef)
                        .findFirst();

                if (defaultDiscount.isPresent()) {
                    Discount discount = defaultDiscount.get();
                    p.setDiscountPercent(discount.getPercent());
                    p.setFinalPrice(p.getPrice() - p.getPrice() * discount.getPercent() / 100);
                } else {
                    p.setDiscountPercent(0);
                    p.setFinalPrice(p.getPrice());
                }

                double total = p.getVotes().stream().mapToInt(Vote::getStars).sum();
                double avg = total / p.getVotes().size();
                avg = Math.round(avg * 10) / 10.0;
                p.setAvgVote(avg);
            });
            model.addAttribute("list_post_all",  list);
            log.info(" ID {}",id);
            log.info(" Buyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy: {}",buy);
            buy.forEach(p -> {
                        Optional<Discount> defaultDiscount = p.getDiscounts()
                                .stream()
                                .filter(Discount::isDef)
                                .findFirst();

                        if (defaultDiscount.isPresent()) {
                            Discount discount = defaultDiscount.get();
                            p.setDiscountPercent(discount.getPercent());
                            p.setFinalPrice(p.getPrice() - p.getPrice() * discount.getPercent() / 100);
                        } else {
                            p.setDiscountPercent(0);
                            p.setFinalPrice(p.getPrice());
                        }

                        double total = p.getVotes().stream().mapToInt(Vote::getStars).sum();
                        double avg = total / p.getVotes().size();
                        avg = Math.round(avg * 10) / 10.0;
                        p.setAvgVote(avg);
            });
            model.addAttribute("post_buy",  buy);

            model.addAttribute("c_buy",  buy.size());
            model.addAttribute("c_sale",  list.getContent().size());

        return "profile";
    }

    @GetMapping("/post")
    public String post(Authentication authentication, Model model){
        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            UserResponse u =userService.getUserByUsername(username) ;
            boolean isTeacher = u.getRoles().stream()
                    .anyMatch(role -> role.getName().equals(PredefinedRole.TEACHER_ROLE));
            if(!isTeacher) {
                model.addAttribute("isTeacher", "No");
            } else model.addAttribute("isTeacher", "Yes");
            model.addAttribute("user", u);
        }
        return "create-step1-post";
    }
    @GetMapping("/setting")
    public String setting(Authentication authentication, Model model ){
        if(authentication!=null) {
            model.addAttribute("card", new Card());
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        return "setting-profile";
    }
    @PostMapping("/add-card")
    public String addCard(@ModelAttribute Card card,
                          @RequestHeader(value = "Referer", required = false ) String referer ){
        System.out.println("Card Details: " + card);
        userService.addCard(card,card.getId());
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:/profile/setting";
    }
}
