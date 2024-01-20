package com.example.holidayswap.repository.exchange;

import com.example.holidayswap.domain.entity.exchange.Exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    Page<Exchange> findAllByRequestUserIdEqualsOrUserIdEquals(Long requestUserId, Long userId, Pageable pageable);
}
