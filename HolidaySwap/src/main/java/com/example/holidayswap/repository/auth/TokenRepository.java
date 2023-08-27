package com.example.holidayswap.repository.auth;

import com.example.holidayswap.domain.entity.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
