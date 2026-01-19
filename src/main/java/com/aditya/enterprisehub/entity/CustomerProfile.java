package com.aditya.enterprisehub.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "customer_profiles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_id")
        }
)
public class CustomerProfile extends AuditableEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}