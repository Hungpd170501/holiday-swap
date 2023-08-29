package com.example.holidayswap.repository.auth;

import com.example.holidayswap.domain.entity.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.userId = u.userId\s
      where u.userId = :id and (t.status = "VALID")\s
      """)
    List<Token> findAllValidTokenByUser(Long id);
    Optional<Token> findByValueEquals(String token);
}
