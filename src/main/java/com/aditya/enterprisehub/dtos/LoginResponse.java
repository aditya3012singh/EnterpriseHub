package com.aditya.enterprisehub.dtos;

import com.aditya.enterprisehub.entity.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String message;
    private String email;
    private String token;
    private String tokenType;
//    private Set<RoleType> roles;
}

