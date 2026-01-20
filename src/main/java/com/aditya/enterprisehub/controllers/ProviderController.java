package com.aditya.enterprisehub.controllers;

import com.aditya.enterprisehub.entity.ProviderProfile;
import com.aditya.enterprisehub.entity.User;
import com.aditya.enterprisehub.entity.enums.VerificationStatus;
import com.aditya.enterprisehub.repository.ProviderProfileRepository;
import com.aditya.enterprisehub.repository.UserRepository;
import com.aditya.enterprisehub.services.ProviderOnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/provider")
public class ProviderController {
    private final ProviderOnboardingService providerOnboardingService;
    private final UserRepository userRepository;
    private final ProviderProfileRepository providerRepository;
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/request")
    public ProviderProfile requestProvider(Authentication authentication) {
        ProviderProfile profile= providerOnboardingService.createProvider(authentication);

        return profile;
    }

    @GetMapping
    public String hello(){
        return "hello";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/submit")
    public String submitForVerification(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();

        ProviderProfile profile = providerRepository
                .findByUserId(user.getId())
                .orElseThrow();

        profile.setVerificationStatus(VerificationStatus.PENDING);

        providerRepository.save(profile);

        return "Verification submitted";
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @GetMapping("/provider/dashboard")
    public String dashboard() {
        return "Welcome Provider";
    }

}
