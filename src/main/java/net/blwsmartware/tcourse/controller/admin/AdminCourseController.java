package net.blwsmartware.tcourse.controller.admin;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.constant.PagePrepare;
import net.blwsmartware.tcourse.dto.request.account.UserRequest;
import net.blwsmartware.tcourse.dto.request.post.CategoryRequest;
import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.entity.Invoice;
import net.blwsmartware.tcourse.service.CategoryService;
import net.blwsmartware.tcourse.service.InvoiceService;
import net.blwsmartware.tcourse.service.PostService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequestMapping("/admin")
public class AdminCourseController {

    PostService postService;
    InvoiceService invoiceService;
    UserService userService;
    CategoryService categoryService;
    @PostMapping("/category/create")
    public String createCategory(@Valid CategoryRequest categoryRequest, RedirectAttributes attributes ,
                                 @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            categoryService.createCategory(categoryRequest);
            attributes.addFlashAttribute("success", "successfully!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error : " + e.getMessage());
        }
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:admin/cate?bc=ALL";
    }
    @PostMapping("/category/delete")
    public String deleteCategory(@RequestParam Long cateId, RedirectAttributes attributes ,
                             @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            categoryService.delete(cateId);
            attributes.addFlashAttribute("success", "deleted successfully!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error deleting : " + e.getMessage());
        }
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:admin/cate?bc=ALL";
    }
    @GetMapping({"/course"})
    public String course(Model model, Authentication authentication,
                         @RequestParam(value = "pageNumber",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                         @RequestParam(value = "pageSize", defaultValue = PagePrepare.PAGE_SIZE, required = false) Integer pageSize,
                         @RequestParam(value = "c", defaultValue = PagePrepare.CATEGORY, required = false) List<String> query,
                         @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy){

        model.addAttribute("posts",postService.getAll() );
        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        return "admin/course-dashboard";
    }
    @GetMapping({"/cate"})
    public String cate(Model model,Authentication authentication,
                       @RequestParam(value = "pageNumber",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PagePrepare.PAGE_SIZE, required = false) Integer pageSize,
                       @RequestParam(value = "bc", defaultValue = PagePrepare.CATEGORY, required = false) List<String> query,
                       @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy){

        model.addAttribute("categories",categoryService.getAll() );
        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        return "admin/category-dashboard";
    }
    @GetMapping({"/invoice"})
    public String invoice(Model model,Authentication authentication,
                          @RequestParam(value = "pageNumber",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PagePrepare.PAGE_SIZE, required = false) Integer pageSize,
                       @RequestParam(value = "bc", defaultValue = PagePrepare.CATEGORY, required = false) String query,
                       @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy){
        List<Invoice> list = invoiceService.getAll();
        int totalSum = list.stream()
                .mapToInt(Invoice::getTotal)
                .sum();
        model.addAttribute("invoices", list);
        model.addAttribute("buy", list.size());
        model.addAttribute("total", totalSum);

        model.addAttribute("stu",
                userService.getAllByRoleName(-1,1000,PagePrepare.SORT_BY,List.of("USER")).size());
        int count = list.stream()
                .mapToInt(invoice -> invoice.getDetailList().size())
                .sum();
        model.addAttribute("count", count);

        if(authentication!=null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("user", userService.getUserByUsername(username));
        }
        return "admin/invoice-dashboard";
    }
}
