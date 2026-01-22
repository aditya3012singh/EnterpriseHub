package com.aditya.enterprisehub.controllers;

import com.aditya.enterprisehub.config.auth.CustomUserDetails;
import com.aditya.enterprisehub.entity.Payment;
import com.aditya.enterprisehub.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{bookingId}/initiate")
    public Payment initiatePayment(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal CustomUserDetails principal
    ) {
        return paymentService.initiatePayment(
                bookingId,
                principal.getUserId()
        );
    }
}
