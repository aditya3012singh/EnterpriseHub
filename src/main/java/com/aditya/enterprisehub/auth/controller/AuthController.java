package com.aditya.enterprisehub.auth.controller;

import com.aditya.enterprisehub.auth.dto.LoginRequest;
import com.aditya.enterprisehub.auth.dto.LoginResponse;
import com.aditya.enterprisehub.auth.dto.RegisterRequest;
import com.aditya.enterprisehub.auth.dto.RegisterResponse;
import com.aditya.enterprisehub.auth.service.AuthService;
import com.aditya.enterprisehub.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        System.out.println("LOGIN HIT");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
            System.out.println("AUTHENTICATION PASSED");
        } catch (Exception e) {
            System.out.println("AUTHENTICATION FAILED");
            e.printStackTrace();
            throw e;
        }

        String token = jwtService.generateToken(request.email(), Map.of());
        System.out.println("TOKEN GENERATED: " + token);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest request
    ) {
        authService.register(request);
        return ResponseEntity.ok(
                new RegisterResponse("User registered successfully")
        );
    }

}
