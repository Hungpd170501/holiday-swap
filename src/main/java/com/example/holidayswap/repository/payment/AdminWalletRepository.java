package com.example.holidayswap.repository.payment;

import com.example.holidayswap.domain.entity.payment.AdminWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminWalletRepository extends JpaRepository<AdminWallet,Long> {
    AdminWallet findFirstByOrderByIdDesc();
}
