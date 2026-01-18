package com.aditya.enterprisehub.auth.service;

import com.aditya.enterprisehub.auth.dto.RegisterRequest;
import com.aditya.enterprisehub.user.entity.Role;
import com.aditya.enterprisehub.user.entity.User;
import com.aditya.enterprisehub.user.enums.AuthProvider;
import com.aditya.enterprisehub.user.enums.RoleType;
import com.aditya.enterprisehub.user.enums.UserStatus;
import com.aditya.enterprisehub.user.repository.RoleRepository;
import com.aditya.enterprisehub.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest request){
        if(userRepository.existsByEmail(request.email())){
            throw new RuntimeException("Email already registered");
        }

        Role userRole = roleRepository.findByType(RoleType.USER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .status(UserStatus.ACTIVE)
                .provider(AuthProvider.LOCAL)
                .build();

        user.getRoles().add(userRole);

        userRepository.save(user);
    }
}
