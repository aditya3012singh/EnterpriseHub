package com.aditya.enterprisehub.entity;

import com.aditya.enterprisehub.entity.common.AuditableEntity;
import com.aditya.enterprisehub.entity.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "provider_profiles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderProfile extends AuditableEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus= VerificationStatus.NOT_SUBMITTED;

    private boolean active = false;

    private Double rating;
}
