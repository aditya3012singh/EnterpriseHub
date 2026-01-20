package com.aditya.enterprisehub.entity.enums;

public enum BookingStatus {
    PENDING,        // created but not accepted
    CONFIRMED,      // provider accepted
    IN_PROGRESS,    // service ongoing
    PAID,
    COMPLETED,      // service done
    CANCELLED,       // cancelled by user/provider
    REJECTED
}
