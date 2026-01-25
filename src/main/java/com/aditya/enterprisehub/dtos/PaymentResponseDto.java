package com.aditya.enterprisehub.dtos;

import com.aditya.enterprisehub.entity.Booking;
import com.aditya.enterprisehub.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private String message;
    private Booking booking;
    private Double amount;
    private PaymentStatus status;
    private String gatewayOrderId;
    private String gatewayPaymentId;
    private String gatewaySignature;
}
