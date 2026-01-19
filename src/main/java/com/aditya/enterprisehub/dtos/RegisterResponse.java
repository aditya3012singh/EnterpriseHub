package com.aditya.enterprisehub.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private String email;
    private String token;
    private String tokenType;
//    private Set<String> roles;
}