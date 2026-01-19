package com.aditya.enterprisehub.entity;

import com.aditya.enterprisehub.entity.enums.VerificationStatus;
import jakarta.persistence.*;

@Entity
@Table(
        name = "provider_profiles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_id")
        }
)
public class ProviderProfile extends AuditableEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String bio;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus= VerificationStatus.NOT_SUBMITTED;

    private boolean active = false;

    private Double rating;
}
