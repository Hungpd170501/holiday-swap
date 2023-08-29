package com.example.holidayswap.repository.auth;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUserIdEquals(Long userId);
    Optional<User> getUserByEmailEquals(String email);
    Optional<User> getUserByEmailEqualsAndStatusEquals(String email, UserStatus status);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

