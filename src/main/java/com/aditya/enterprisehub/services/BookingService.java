package com.aditya.enterprisehub.services;

import com.aditya.enterprisehub.entity.Booking;
import com.aditya.enterprisehub.entity.ProviderService;
import com.aditya.enterprisehub.entity.User;
import com.aditya.enterprisehub.entity.enums.BookingStatus;
import com.aditya.enterprisehub.repository.BookingRepository;
import com.aditya.enterprisehub.repository.ProviderServiceRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ProviderServiceRepository providerServiceRepository;
    private final EntityManager entityManager;
    @Transactional
    public Booking createBooking(
            Long userId,
            Long providerServiceId,
            Instant startTime
    ) {

        // 1️⃣ Load provider-service mapping
        ProviderService providerService =
                providerServiceRepository.findById(providerServiceId)
                        .orElseThrow(() -> new RuntimeException("Service not found"));

        // 2️⃣ Calculate end time
        Instant endTime =
                startTime.plus(
                        providerService.getDurationInMinutes(),
                        ChronoUnit.MINUTES
                );

        // 3️⃣ Conflict detection (PENDING + CONFIRMED)
        boolean conflict =
                bookingRepository.existsConflictingBooking(
                        providerService.getProvider().getId(),
                        startTime,
                        endTime,
                        List.of(
                                BookingStatus.PENDING,
                                BookingStatus.CONFIRMED
                        )
                );

        if (conflict) {
            throw new RuntimeException("Provider already booked for this slot");
        }


        // ✅ reference only (no DB hit)
        User userRef = entityManager.getReference(User.class, userId);
        // 4️⃣ Create booking
        Booking booking = Booking.builder()
                .user(userRef) // safe reference
                .providerService(providerService)
                .startTime(startTime)
                .endTime(endTime)
                .status(BookingStatus.PENDING)
                .build();

        return bookingRepository.save(booking);
    }
}
