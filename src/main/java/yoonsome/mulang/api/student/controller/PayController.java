package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yoonsome.mulang.api.student.dto.MypageResponse;
import yoonsome.mulang.api.student.dto.PaymentResponse;
import yoonsome.mulang.api.student.service.MypagePaymentService;
import yoonsome.mulang.api.student.service.MypageService;
import yoonsome.mulang.domain.payment.service.PaymentService;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;

@RequestMapping("/student")
@Controller
@RequiredArgsConstructor
public class PayController {

    private final MypagePaymentService mypagePaymentService;
    private final MypageService mypageService;

    @GetMapping("pay")
    public String pay(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        Long userId = userDetails.getUser().getId();

        List<PaymentResponse> paymentResponseList = mypagePaymentService.findByUserId(userId);
        int totalsize = paymentResponseList.size();
        int totalamount = 0;

        for(PaymentResponse paymentResponse : paymentResponseList){
            totalamount += paymentResponse.getCourse().getPrice();
        }



        MypageResponse user = mypageService.getUserInfo(userId);
        model.addAttribute("paymentResponseList", paymentResponseList);
        model.addAttribute("totalsize", totalsize);
        model.addAttribute("totalamount", totalamount);
        model.addAttribute("user", user);

        return "student/payhistory/pay";
    }

}
