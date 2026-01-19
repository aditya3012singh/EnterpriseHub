package com.aditya.enterprisehub.services.impl;

import com.aditya.enterprisehub.config.security.JwtService;
import com.aditya.enterprisehub.dtos.LoginRequest;
import com.aditya.enterprisehub.dtos.LoginResponse;
import com.aditya.enterprisehub.dtos.RegisterRequest;
import com.aditya.enterprisehub.dtos.RegisterResponse;
import com.aditya.enterprisehub.entity.Role;
import com.aditya.enterprisehub.entity.User;
import com.aditya.enterprisehub.repository.RoleRepository;
import com.aditya.enterprisehub.repository.UserRepository;
import com.aditya.enterprisehub.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        // 1️⃣ Check if user already exists
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("User already exists with email: " + request.email());
        }

        // 2️⃣ Fetch role
        Role role = roleRepository.findByType(request.role())
                .orElseThrow(() -> new RuntimeException("Role not found: " + request.role()));

        // 🔐 Optional: prevent ADMIN registration
        if (request.role().name().equals("ADMIN")) {
            throw new RuntimeException("Admin registration not allowed");
        }

        // 3️⃣ Create user entity
        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(role));
        user.setEnabled(true);

        // 4️⃣ Save user
        userRepository.save(user);

        // 5️⃣ Load UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        // 6️⃣ Generate JWT
        String token = jwtService.generateToken(userDetails);

        return RegisterResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .email(user.getEmail())
                .message("User registered successfully")
                .build();
    }


    @Override
    public LoginResponse login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        Set<String> roles = user.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toSet());

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .email(user.getUsername())
//                .roles(roles)
                .message("Login successful")
                .build();
    }

}
