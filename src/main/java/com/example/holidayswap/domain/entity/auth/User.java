package com.example.holidayswap.domain.entity.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "user_id"
    )
    private Long userId;

    @NotBlank
    @Email
    @Column(
            nullable = false,
            unique = true
    )
    private String email;

    @Column
    private String passwordHash;

    @NotEmpty
    @Nationalized
    @Column(nullable = false,
            unique = true
    )
    private String username;

    @Column
    private String avatar;

    @NotNull(message = "Gender must be specified.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NotNull(message = "Date of birth must be specified.")
    @Column(name = "dob")
    private LocalDate dob;

    @Column
    private String phone;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "phone_verified")
    private boolean phoneVerified;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany
    @JoinTable(name = "user_blocked",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "blocked_user_id"))
    private List<User> userBlockedList;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
