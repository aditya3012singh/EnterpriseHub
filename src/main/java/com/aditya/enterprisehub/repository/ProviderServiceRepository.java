package com.aditya.enterprisehub.repository;

import com.aditya.enterprisehub.entity.ProviderService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderServiceRepository extends JpaRepository<ProviderService, Long> {

}