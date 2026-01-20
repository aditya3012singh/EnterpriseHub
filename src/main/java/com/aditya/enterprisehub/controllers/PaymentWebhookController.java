package com.aditya.enterprisehub.controllers;

import com.aditya.enterprisehub.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webhooks/payment")
public class PaymentWebhookController {

    private final PaymentService paymentService;

    @PostMapping
    public void handlePaymentCallback(
            @RequestParam String razorpay_order_id,
            @RequestParam String razorpay_payment_id,
            @RequestParam String razorpay_signature
    ) {
        paymentService.verifyPayment(
                razorpay_order_id,
                razorpay_payment_id,
                razorpay_signature
        );
    }
}
