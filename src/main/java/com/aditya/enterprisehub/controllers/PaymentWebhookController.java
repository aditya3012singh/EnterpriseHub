package com.aditya.enterprisehub.controllers;

import com.aditya.enterprisehub.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Void> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String signature
    ) {
        paymentService.handleWebhook(payload, signature);
        return ResponseEntity.ok().build();
    }
}
