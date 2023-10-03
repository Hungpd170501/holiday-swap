package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("select p from Property p " +
           "join p.propertyType pt " +
           "join  pt.resorts r " +
           "where r.id = ?1 " +
           "and p.isDeleted = false " +
           "and pt.isDeleted = false " +
           "and r.isDeleted = false ")
    Page<Property> findAllByResortIdAndIsDeleteIsFalse(Long resortId, Pageable pageable);

    @Query("select p from Property p " +
           "join p.propertyType pt " +
           "join  pt.resorts r " +
           "where r.id = ?1 " +
           "and p.isDeleted = false " +
           "and pt.isDeleted = false " +
           "and r.isDeleted = false ")
    List<Property> findAllByResortIdAndIsDeleteIsFalse(Long resortId);

    @Query(value = "select distinct p from Property p " +
                   "inner join PropertyType pt on p.propertyTypeId = pt.id " +
                   "inner join pt.resorts r " +
                   "inner join  Ownership o on p.propertyTypeId = o.id.propertyId " +
                   "inner join VacationUnit v on v.propertyId = p.id " +
                   "inner join TimeOffDeposit tod on tod.vacationUnitId = v.id " +
                   "where r.id = ?1 " +
                   "and p.isDeleted = false " +
                   "and pt.isDeleted = false " +
                   "and r.isDeleted = false " +
                   "and o.isDeleted = false " +
                   "and v.isDeleted = false " +
                   "and tod.isDeleted = false " +
                   "and (tod.startTime between ?2  and ?3 or tod.endTime  between ?2  and ?3)")
    Page<Property> findAllByResortIdAndIsDeleteIsFalseIncludeCheckInCheckOut(Long resortId,
                                                                             Date timeCheckIn,
                                                                             Date timeCheckOut,
                                                                             Pageable pageable);

    @Query("select p from Property p where p.id = ?1 and p.isDeleted = false ")
    Optional<Property> findPropertyByIdAndIsDeletedIsFalse(Long propertyId);
}
