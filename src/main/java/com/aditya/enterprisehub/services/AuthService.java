package com.aditya.enterprisehub.services;

import com.aditya.enterprisehub.dtos.LoginRequest;
import com.aditya.enterprisehub.dtos.LoginResponse;
import com.aditya.enterprisehub.dtos.RegisterRequest;
import com.aditya.enterprisehub.dtos.RegisterResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    RegisterResponse register(RegisterRequest request);
}
