package com.aditya.enterprisehub.entity;

import com.aditya.enterprisehub.entity.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "provider_services",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider_id", "service_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderService extends AuditableEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id")
    private ProviderProfile provider;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private Service service;

    /**
     * Price charged by provider for this service
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Estimated duration in minutes
     */
    @Column(nullable = false)
    private Integer durationInMinutes;

    /**
     * Experience of provider for this service (in years)
     */
    @Column(nullable = false)
    private Integer experienceInYears;

    /**
     * Whether provider is currently offering this service
     */
    private boolean active= true;
}
