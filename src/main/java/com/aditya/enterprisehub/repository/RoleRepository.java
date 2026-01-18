package com.aditya.enterprisehub.repository;

import com.aditya.enterprisehub.entity.Role;
import com.aditya.enterprisehub.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByType(RoleType type);
}
