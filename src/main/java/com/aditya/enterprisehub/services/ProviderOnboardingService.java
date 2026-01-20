package com.aditya.enterprisehub.services;

import com.aditya.enterprisehub.entity.ProviderProfile;
import com.aditya.enterprisehub.entity.User;
import com.aditya.enterprisehub.entity.enums.VerificationStatus;
import com.aditya.enterprisehub.repository.ProviderProfileRepository;
import com.aditya.enterprisehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

//
//Handles provider onboarding
//Creates ProviderProfile
//Works with User + ProviderProfile
@Service
@RequiredArgsConstructor
public class ProviderOnboardingService {
    private final UserRepository userRepository;
    private final ProviderProfileRepository providerRepository;

    public ProviderProfile createProvider(Authentication authentication){
        String email= authentication.getName();

        User user= userRepository.findByEmail(email).orElseThrow();

        if(providerRepository.existsByUserId(user.getId())){
            throw new IllegalStateException("Already requested provider access");
        }

        ProviderProfile profile= ProviderProfile.builder().user(user)
                .verificationStatus(VerificationStatus.NOT_SUBMITTED)
                .active(false)
                .build();

        providerRepository.save(profile);

        return profile;
    }
}
