package com.aditya.enterprisehub.controllers;

import com.aditya.enterprisehub.entity.ProviderProfile;
import com.aditya.enterprisehub.entity.Role;
import com.aditya.enterprisehub.entity.User;
import com.aditya.enterprisehub.entity.enums.RoleType;
import com.aditya.enterprisehub.entity.enums.VerificationStatus;
import com.aditya.enterprisehub.repository.ProviderProfileRepository;
import com.aditya.enterprisehub.repository.RoleRepository;
import com.aditya.enterprisehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/providers")
public class AdminProviderController {

    private final ProviderProfileRepository providerRepo;
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{providerId}/approve")
    public String approveProvider(@PathVariable Long providerId) {

        ProviderProfile profile = providerRepo.findById(providerId)
                .orElseThrow();

        profile.setVerificationStatus(VerificationStatus.APPROVED);
        profile.setActive(true);

        User user = profile.getUser();

        Role providerRole = roleRepo.findByType(RoleType.PROVIDER)
                .orElseThrow();

        user.getRoles().add(providerRole);

        userRepo.save(user);
        providerRepo.save(profile);

        return "Provider approved";
    }
}
