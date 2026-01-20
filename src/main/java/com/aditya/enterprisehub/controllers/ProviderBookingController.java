//package com.aditya.enterprisehub.controllers;
//
//import com.aditya.enterprisehub.entity.Booking;
//import com.aditya.enterprisehub.services.ProviderBookingService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/provider/bookings")
//@PreAuthorize("hasRole('PROVIDER')")
//public class ProviderBookingController {
//
//    private final ProviderBookingService bookingService;
//
//    @PostMapping("/{bookingId}/accept")
//    public Booking accept(
//            @PathVariable Long bookingId,
//            @AuthenticationPrincipal UserPrincipal principal
//    ) {
//        return bookingService.acceptBooking(
//                bookingId,
//                principal.getUserId()
//        );
//    }
//
//    @PostMapping("/{bookingId}/reject")
//    public Booking reject(
//            @PathVariable Long bookingId,
//            @AuthenticationPrincipal UserPrincipal principal
//    ) {
//        return bookingService.rejectBooking(
//                bookingId,
//                principal.getUserId()
//        );
//    }
//}
