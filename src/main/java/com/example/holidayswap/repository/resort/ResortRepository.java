package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.entity.resort.Resort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Long> {
    Page<Resort> findAllByDeletedIsFalse(Pageable pageable);
}
