package com.example.holidayswap.domain.entity.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "token_id"
    )
    private Long tokenId;

    @NotNull(message = "Token type must be specified.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "token_type")
    private TokenType tokenType;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false, name = "expiration_time")
    private LocalDateTime expirationTime;

    @NotNull(message = "Token status must be specified.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
