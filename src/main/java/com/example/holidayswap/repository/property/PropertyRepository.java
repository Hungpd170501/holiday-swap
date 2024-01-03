package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.resort.Resort;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("select p from Property p " +
            "join p.propertyType pt " +
            "join  pt.resorts r " +
            "where p.resortId = ?1 " +
            "and p.isDeleted = false " +
            "and pt.isDeleted = false " +
            "and r.isDeleted = false ")
    List<Property> findAllByResortIdAndIsDeleteIsFalse(Long resortId);
    //get all for staff
    @Query(value = """
            select distinct p from Property p         
            where upper(p.propertyName) like upper(concat('%', :propertyName, '%'))
            and p.isDeleted = false 
            and ((:#{#resortId == null} = true) or (p.resortId in :resortId))
            and ((:#{#propertyStatus == null} = true) or (p.status in :propertyStatus))
            """)
    Page<Property> findAllByFilter(@Param("resortId") Long[] resortId,
                                   @Param("propertyName") String propertyName,
                                   @Param("propertyStatus") PropertyStatus[] propertyStatus,

                                   Pageable pageable);

    @Query(value = """
            select distinct p from Property p 
            left join p.resort r    
            where 
            p.isDeleted = false  and r.isDeleted = false 
            and p.status = 'ACTIVE' and r.status = 'ACTIVE'
            and r.id = :resort_id
            """)
    List<Property> getListPropertyActive(@Param("resort_id") Long resort_id);

    @Query("select p from Property p where p.id = ?1 and p.isDeleted = false ")
    Optional<Property> findPropertyByIdAndIsDeletedIsFalse(Long propertyId);

    Optional<Property> findPropertyByIdAndIsDeletedIsFalseAndStatus(Long propertyId, PropertyStatus propertyStatus);

    @Query("select r from Property r where r.id = :resortId and r.isDeleted = false and r.status = :resortStatus")
    Optional<Property> findByIdAndDeletedFalseAndResortStatus(@Param(("resortId")) Long id, @Param(("resortStatus")) PropertyStatus resortStatus);
}
