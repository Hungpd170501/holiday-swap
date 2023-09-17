package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.ContractImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractImageRepository extends JpaRepository<ContractImage, Long> {
    List<ContractImage> findContractImageByPropertyContractId(Long contractId);
}
