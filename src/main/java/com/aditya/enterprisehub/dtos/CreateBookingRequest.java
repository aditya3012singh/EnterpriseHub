package com.aditya.enterprisehub.dtos;

import java.time.Instant;

public class CreateBookingRequest {
    Long userId;
    Long providerServiceId;
    Instant startTime;
}
