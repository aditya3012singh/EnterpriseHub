package com.aditya.enterprisehub.entity;

import com.aditya.enterprisehub.entity.common.AuditableEntity;
import com.aditya.enterprisehub.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends AuditableEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "provider_id")
//    private ProviderProfile provider;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "service_id")
//    private Service service;
//

    /**
     * Links provider + service + price + duration
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_service_id", nullable = false)
    private ProviderService providerService;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    /**
     * Scheduled start time
     */
//    @Column(nullable = false)
//    private LocalDateTime scheduledAt;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;

    /**
     * Price locked at booking time
     */
    @Column(nullable = false)
    private BigDecimal bookedPrice;

    /**
     * Duration locked at booking time
     */
    @Column(nullable = false)
    private Integer bookedDurationInMinutes;

    private String notes;

    @Version
    private Long version;
}
