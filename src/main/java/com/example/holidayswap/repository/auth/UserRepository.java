package com.example.holidayswap.repository.auth;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUserIdEquals(Long userId);

    Optional<User> getUserByEmailEquals(String email);

    @Query("""
            SELECT u FROM User u
            INNER JOIN Role r
            ON u.role.roleId = r.roleId
            WHERE
            (:email = '' OR u.email LIKE %:email%)
            AND (:name = '' OR unaccent(upper(u.username)) LIKE %:name%)
            AND (:phone = '' OR u.phone LIKE %:phone%)
            AND ((:#{#statusSet.empty} = true) OR u.status IN :statusSet)
            AND ((:#{#roleIds.empty} = true) OR u.role.roleId IN :roleIds)
            """)
        //    CREATE EXTENSION unaccent; in postgresql
    Page<User> findAllByEmailNamePhoneStatusRoleWithPagination(
            String email, String name, String phone, Set<UserStatus> statusSet, Set<Long> roleIds, Pageable pageable);

    Optional<User> getUserByEmailEqualsAndStatusEquals(String email, UserStatus status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByUsername(String username);
}

