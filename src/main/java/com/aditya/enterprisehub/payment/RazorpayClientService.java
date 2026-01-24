package com.aditya.enterprisehub.payment;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RazorpayClientService {

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    private RazorpayClient client;

    @PostConstruct
    public void init() throws Exception {
        this.client = new RazorpayClient(key, secret);
    }

//    Why @PostConstruct?
//
//    Spring injects values after constructor
//
//    RazorpayClient needs keys
//
//    Clean lifecycle management

    /**
     * Create Razorpay order
     */
    public String createOrder(Double amount, Long bookingId) {

        try {
            JSONObject request = new JSONObject();
            request.put("amount", (int) (amount * 100)); // paise
            request.put("currency", "INR");
            request.put("receipt", "booking_" + bookingId);
            request.put("payment_capture", 1);

            Order order = client.orders.create(request);

            return order.get("id");

        } catch (Exception e) {
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }

    /**
     * Verify payment signature (CRITICAL)
     */
    public boolean verifySignature(
            String orderId,
            String paymentId,
            String signature
    ) {
        try {
            JSONObject params = new JSONObject();
            params.put("razorpay_order_id", orderId);
            params.put("razorpay_payment_id", paymentId);
            params.put("razorpay_signature", signature);

            return Utils.verifyPaymentSignature(params, secret);

        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyWebhook(String payload, String signature) {
        try {
            Utils.verifyWebhookSignature(
                    payload,
                    signature,
                    "RAZORPAY_WEBHOOK_SECRET"
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
