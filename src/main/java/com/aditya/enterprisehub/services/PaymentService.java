package com.aditya.enterprisehub.services;

import com.aditya.enterprisehub.entity.Booking;
import com.aditya.enterprisehub.entity.Payment;
import com.aditya.enterprisehub.entity.enums.BookingStatus;
import com.aditya.enterprisehub.entity.enums.PaymentStatus;
import com.aditya.enterprisehub.payment.RazorpayClientService;
import com.aditya.enterprisehub.repository.BookingRepository;
import com.aditya.enterprisehub.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final RazorpayClientService razorpayClientService;

    /**
     * USER initiates payment
     */
    @Transactional
    public Payment initiatePayment(Long bookingId, Long userId) {

        // 1️⃣ Load booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 2️⃣ Ownership check
        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized payment attempt");
        }

        // 3️⃣ Booking must be CONFIRMED
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException("Booking not ready for payment");
        }

        // 4️⃣ Prevent duplicate payments
        if (paymentRepository.existsByBookingId(bookingId)) {
            throw new RuntimeException("Payment already initiated");
        }

        // 5️⃣ Create payment record
        Payment payment = Payment.builder()
                .booking(booking)
                .amount(booking.getProviderService().getPrice())
                .status(PaymentStatus.INITIATED)
                .build();

        paymentRepository.save(payment);

        // 6️⃣ Create gateway order
        String orderId =
                razorpayClientService.createOrder(
                        payment.getAmount(),
                        booking.getId()
                );

        payment.setGatewayOrderId(orderId);

        return payment;
    }

    /**
     * Razorpay webhook verification
     */
    @Transactional
    public void verifyPayment(
            String orderId,
            String paymentId,
            String signature
    ) {

        Payment payment =
                paymentRepository.findByGatewayOrderId(orderId)
                        .orElseThrow(() -> new RuntimeException("Payment not found"));

        boolean valid =
                razorpayClientService.verifySignature(
                        orderId,
                        paymentId,
                        signature
                );

        if (!valid) {
            payment.setStatus(PaymentStatus.FAILED);
            return;
        }

        // 1️⃣ Mark payment success
        payment.setGatewayPaymentId(paymentId);
        payment.setGatewaySignature(signature);
        payment.setStatus(PaymentStatus.SUCCESS);

        // 2️⃣ Update booking
        Booking booking = payment.getBooking();
        booking.setStatus(BookingStatus.PAID);
    }
}
