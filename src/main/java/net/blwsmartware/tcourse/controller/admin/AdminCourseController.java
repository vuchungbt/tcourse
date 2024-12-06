package net.blwsmartware.tcourse.controller.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.constant.PagePrepare;
import net.blwsmartware.tcourse.entity.Invoice;
import net.blwsmartware.tcourse.service.InvoiceService;
import net.blwsmartware.tcourse.service.PostService;
import net.blwsmartware.tcourse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping({"/course"})
    public String home(Model model,
                       @RequestParam(value = "pageNumber",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PagePrepare.PAGE_SIZE, required = false) Integer pageSize,
                       @RequestParam(value = "c", defaultValue = PagePrepare.CATEGORY, required = false) List<String> query,
                       @RequestParam(value = "sortBy",defaultValue = PagePrepare.SORT_BY, required = false) String sortBy){

        model.addAttribute("posts",postService.getAll() );

        return "admin/course-dashboard";
    }
    @GetMapping({"/invoice"})
    public String invoice(Model model,
                       @RequestParam(value = "pageNumber",defaultValue = PagePrepare.PAGE_NUMBER,required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PagePrepare.PAGE_SIZE, required = false) Integer pageSize,
                       @RequestParam(value = "i", defaultValue = PagePrepare.CATEGORY, required = false) List<String> query,
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


        return "admin/invoice-dashboard";
    }
}
