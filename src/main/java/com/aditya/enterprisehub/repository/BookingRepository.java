package com.aditya.enterprisehub.repository;

import com.aditya.enterprisehub.entity.Booking;
import com.aditya.enterprisehub.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
        SELECT COUNT(b) > 0
        FROM Booking b
        WHERE b.providerService.provider.id = :providerId
        AND b.status IN (:activeStatuses)
        AND b.startTime < :endTime
        AND b.endTime > :startTime
    """)
    boolean existsConflictingBooking(
            @Param("providerId") Long providerId,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime,
            @Param("activeStatuses") List<BookingStatus> activeStatuses
    );
}