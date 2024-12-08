package net.blwsmartware.tcourse.controller.web;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.post.InvoiceRequest;
import net.blwsmartware.tcourse.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class PaymentController {

    InvoiceService invoiceService;
    UserService userService;
    @PostMapping("/home/buy")
    public  ResponseEntity<?> createComment(@Valid @RequestBody InvoiceRequest invoiceRequest,
                                Model model ,
                                Authentication authentication,
                                @RequestHeader(value = "Referer", required = false) String referer) {
        if(authentication==null) {
            return ResponseEntity.ok()
                    .body(Map.of(
                            "success", false,
                            "login",false,
                            "message", "Please login"
                    ));
        }
        String username = authentication.getName();
        if(userService.getUserByUsername(username).getCards().isEmpty() ) {
            return ResponseEntity.ok()
                    .body(Map.of(
                            "success", false,
                            "card" , 0 ,
                            "message", "Bạn chưa thêm thẻ thanh toán"
                    ));
        }
        invoiceRequest.setCreateBy(username);

        invoiceService.addInvoice(invoiceRequest);

        return ResponseEntity.ok()
                .body(Map.of(
                        "success", true,
                        "message", "Created invoice successfully"
                ));

    }


}
