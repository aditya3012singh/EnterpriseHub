package com.aditya.enterprisehub.entity;

import com.aditya.enterprisehub.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "roles",
        uniqueConstraints ={
                @UniqueConstraint(name = "uk_role_type", columnNames = "type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleType type;
}
