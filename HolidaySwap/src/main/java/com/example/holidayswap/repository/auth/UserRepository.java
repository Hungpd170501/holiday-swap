package com.example.holidayswap.repository.auth;

import com.example.holidayswap.domain.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUserIdEquals(Long userId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

