package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.PropertyView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyViewRepository extends JpaRepository<PropertyView, Long> {
    @Query("""
            select p from PropertyView p
            where upper(p.propertyViewName) like upper(concat('%', ?1, '%')) and p.isDeleted = false""")
    Page<PropertyView> findAllByPropertyViewNameContainingIgnoreCaseAndIsDeletedIsFalse(String name, Pageable pageable);

    @Query("select p from PropertyView p where p.id = ?1 and p.isDeleted = false")
    Optional<PropertyView> findByIdAndIsDeletedIsFalse(Long id);

    @Query("select p from PropertyView p where upper(p.propertyViewName) = upper(?1) and p.isDeleted = false")
    Optional<PropertyView> findByPropertyViewNameEqualsIgnoreCaseAndIsDeletedIsFalse(String name);
}
