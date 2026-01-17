package com.aditya.enterprisehub.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;
}
