package com.aditya.enterprisehub.dtos;

import com.aditya.enterprisehub.entity.enums.RoleType;

public record RegisterRequest(String email, String password, RoleType role){
}
