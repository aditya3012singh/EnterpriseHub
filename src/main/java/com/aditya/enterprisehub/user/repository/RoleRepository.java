package com.aditya.enterprisehub.user.repository;

import com.aditya.enterprisehub.user.entity.Role;
import com.aditya.enterprisehub.user.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByType(RoleType type);
}
