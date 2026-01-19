package com.aditya.enterprisehub.entity;

import jakarta.persistence.*;
import lombok.*;

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
public class ProviderService extends AuditableEntity{

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id")
    private ProviderProfile provider;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private Service service;

    private boolean active= true;
}
