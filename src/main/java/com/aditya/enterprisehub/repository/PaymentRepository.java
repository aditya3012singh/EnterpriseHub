package com.aditya.enterprisehub.repository;

import com.aditya.enterprisehub.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByGatewayOrderId(String gatewayOrderId);

    boolean existsByBookingId(Long bookingId);
}
