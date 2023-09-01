package com.example.holidayswap.repository.auth;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUserIdEquals(Long userId);
    Optional<User> getUserByEmailEquals(String email);
    @Query("""
            SELECT u FROM User u WHERE 
            (:email = '' OR u.email LIKE %:email%)
            AND (:name = '' OR unaccent(u.username) LIKE unaccent(concat('%', :name, '%')))
            AND (:phone = '' OR u.phone LIKE %:phone%)
            """)
        //    CREATE EXTENSION unaccent; in postgresql
    Page<User> findAllByEmailNamePhoneWithPagination(
            String email, String name, String phone, Pageable pageable);
    Optional<User> getUserByEmailEqualsAndStatusEquals(String email, UserStatus status);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

