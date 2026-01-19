package com.aditya.enterprisehub.controllers;

import com.aditya.enterprisehub.dtos.LoginRequest;
import com.aditya.enterprisehub.dtos.LoginResponse;
import com.aditya.enterprisehub.dtos.RegisterRequest;
import com.aditya.enterprisehub.dtos.RegisterResponse;
import com.aditya.enterprisehub.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/hello")
//    public String helloUser() {
//        System.out.println("hello");
//        return "you hit this";
//    }
}
