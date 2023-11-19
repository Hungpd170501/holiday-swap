package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.PropertyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyTypeRespository extends JpaRepository<PropertyType, Long> {
    @Query("""
            select p from PropertyType p
            where upper(p.propertyTypeName) like upper(concat('%', ?1, '%')) and p.isDeleted = false""")
    Page<PropertyType> findAllByPropertyTypeNameContainingIgnoreCaseAndDeletedIsFalse(String name, Pageable pageable);

    @Query("select p from PropertyType p where p.id = ?1 and p.isDeleted = false")
    Optional<PropertyType> findByIdAndIsDeletedFalse(Long id);

    @Query("select p from PropertyType p where upper(p.propertyTypeName) = upper(?1) and p.isDeleted = false")
    Optional<PropertyType> findByPropertyTypeNameEqualsIgnoreCaseAndDeletedIsFalse(String name);


}
