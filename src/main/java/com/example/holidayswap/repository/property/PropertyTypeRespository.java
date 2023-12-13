package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.PropertyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyTypeRespository extends JpaRepository<PropertyType, Long> {
    @Query("""
            select p from PropertyType p
            where upper(p.propertyTypeName) like upper(concat('%', ?1, '%')) and p.isDeleted = false""")
    Page<PropertyType> findAllByPropertyTypeNameContainingIgnoreCaseAndDeletedIsFalse(String name, Pageable pageable);

    @Query("select p from PropertyType p where p.id = ?1 and p.isDeleted = false")
    Optional<PropertyType> findByIdAndIsDeletedFalse(Long id);

    @Query(value = """
            select
                pt.property_type_id ,
                pt.property_type_description ,
                pt.is_deleted ,
                pt.property_type_name
            from
                resorts_property_type_property rptp
                    inner join property_type pt on
                        rptp.property_type_id = pt.property_type_id
            where
                    pt.property_type_id = :property_type_id  and rptp.resort_id = :resort_id
            """, nativeQuery = true)
    Optional<PropertyType> findPropertyTypeIsInResort(@Param("property_type_id") Long property_type_id, @Param("resort_id") Long resort_id);

    @Query(value = """
              select
                                        	pt.property_type_id ,
                                        	pt.property_type_description ,
                                        	pt.is_deleted ,
                                        	pt.property_type_name
                                        from
                                        	resorts_property_type_property rptp
                                        inner join property_type pt on
                                        	rptp.property_type_id = pt.property_type_id
                                        where
                                        	resort_id = :resort_id
            """, nativeQuery = true)
    List<PropertyType> findPropertyTypeIsInResort(@Param("resort_id") Long resort_id);

    @Query("select p from PropertyType p where upper(p.propertyTypeName) = upper(?1) and p.isDeleted = false")
    Optional<PropertyType> findByPropertyTypeNameEqualsIgnoreCaseAndDeletedIsFalse(String name);

    @Query("select p from PropertyType p where p.isDeleted = false")
    List<PropertyType> findAllAndIsDeletedIsFasle();
}
