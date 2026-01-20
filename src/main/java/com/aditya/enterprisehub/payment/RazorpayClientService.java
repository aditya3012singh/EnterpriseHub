package com.aditya.enterprisehub.payment;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayClientService {

    private final RazorpayClient client;

    public RazorpayClientService() throws Exception {
        this.client = new RazorpayClient(
                "RAZORPAY_KEY",
                "RAZORPAY_SECRET"
        );
    }

    public String createOrder(Double amount, Long bookingId) {

        JSONObject request = new JSONObject();
        request.put("amount", amount * 100); // paise
        request.put("currency", "INR");
        request.put("receipt", "booking_" + bookingId);

        Order order = client.orders.create(request);
        return order.get("id");
    }

    public boolean verifySignature(
            String orderId,
            String paymentId,
            String signature
    ) {
        // Razorpay SDK verification logic
        return true; // assume verified for now
    }
}
