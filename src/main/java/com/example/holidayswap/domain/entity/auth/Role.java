package com.example.holidayswap.domain.entity.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "role_id"
    )
    private Long roleId;

    @NotEmpty(message = "Name must be specified.")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "Status must be specified.")
    @Column(nullable = false)
    private boolean status;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @OneToMany(mappedBy = "role",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<User> users;
}