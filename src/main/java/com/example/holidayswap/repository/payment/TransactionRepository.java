package com.example.holidayswap.repository.payment;

import com.example.holidayswap.domain.entity.payment.MoneyTranfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<MoneyTranfer, Long> {

}
