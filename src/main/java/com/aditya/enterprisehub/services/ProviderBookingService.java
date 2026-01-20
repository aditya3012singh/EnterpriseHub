package com.aditya.enterprisehub.services;

import com.aditya.enterprisehub.entity.Booking;
import com.aditya.enterprisehub.entity.enums.BookingStatus;
import com.aditya.enterprisehub.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderBookingService {

    private final BookingRepository bookingRepository;

    @Transactional
    public Booking acceptBooking(Long bookingId, Long providerUserId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 1️⃣ Ownership check
        Long ownerId =
                booking.getProviderService()
                        .getProvider()
                        .getUser()
                        .getId();

        if (!ownerId.equals(providerUserId)) {
            throw new RuntimeException("You are not allowed to accept this booking");
        }

        // 2️⃣ Status check
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking already processed");
        }

        // 3️⃣ Accept
        booking.setStatus(BookingStatus.CONFIRMED);

        return booking;
    }

    @Transactional
    public Booking rejectBooking(Long bookingId, Long providerUserId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Long ownerId =
                booking.getProviderService()
                        .getProvider()
                        .getUser()
                        .getId();

        if (!ownerId.equals(providerUserId)) {
            throw new RuntimeException("You are not allowed to reject this booking");
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking already processed");
        }

        booking.setStatus(BookingStatus.REJECTED);

        return booking;
    }

}

