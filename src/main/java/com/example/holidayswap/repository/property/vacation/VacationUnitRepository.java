package com.example.holidayswap.repository.property.vacation;

import com.example.holidayswap.domain.entity.property.vacation.VacationUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VacationUnitRepository extends JpaRepository<VacationUnit, Long> {
    @Query("select v from VacationUnit v where v.propertyId = ?1 and v.isDeleted = false ")
    Page<VacationUnit> findAllByPropertyId(Long propertyId, Pageable pageable);

    @Query("""
            select v from VacationUnit v
            join v.ownership o
            join o.property p
            join p.propertyType pT
            join pT.resorts s
            where s.id = ?1
            and v.isDeleted = false""")
    Page<VacationUnit> findAllByResortId(Long resostId, Pageable pageable);

    @Query("select v from VacationUnit v where v.id = ?1 and v.isDeleted = false")
    Optional<VacationUnit> findByIdAndIsDeletedIsFalse(Long id);
//    Optional<VacationUnit> findByIdAndDeletedIsFalse(Long id);
}
