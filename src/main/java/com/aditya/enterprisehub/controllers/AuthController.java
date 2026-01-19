package com.aditya.enterprisehub.controllers;

import com.aditya.enterprisehub.config.security.JwtService;
import com.aditya.enterprisehub.dtos.LoginRequest;
import com.aditya.enterprisehub.dtos.RegisterRequest;
import com.aditya.enterprisehub.entity.Role;
import com.aditya.enterprisehub.entity.User;
import com.aditya.enterprisehub.entity.enums.RoleType;
import com.aditya.enterprisehub.entity.enums.UserStatus;
import com.aditya.enterprisehub.repository.RoleRepository;
import com.aditya.enterprisehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        // 1️⃣ Check if user already exists
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("User already exists");
        }
        Role role = roleRepository.findByType(request.role())
                .orElseThrow(() -> new RuntimeException("Role not found: " + request.role()));

        // 2️⃣ Create user entity
        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(role));
        user.setEnabled(true);

        // 3️⃣ Save to DB
        userRepository.save(user);

        // 4️⃣ Load UserDetails (standard way)
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(user.getEmail());

        // 5️⃣ Generate JWT
        return jwtService.generateToken(userDetails);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    public String helloUser() {
        System.out.println("hello");
        return "you hit this";
    }
}
