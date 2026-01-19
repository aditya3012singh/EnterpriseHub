package com.aditya.enterprisehub.entity;

import com.aditya.enterprisehub.entity.enums.AuthProvider;
import com.aditya.enterprisehub.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AuditableEntity {
        @Column(nullable = false)
        private String name;

        @Column(nullable = false, unique = true)
        private String email;

        /**
         * Nullable because OAuth users won't have a password
         */
        @Column
        private String password;

        private boolean enabled = true;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private UserStatus status= UserStatus.ACTIVE;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private AuthProvider provider= AuthProvider.LOCAL;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        @Builder.Default
        private Set<Role> roles = new HashSet<>();
}
