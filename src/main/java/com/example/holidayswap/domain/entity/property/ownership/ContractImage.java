package com.example.holidayswap.domain.entity.property.ownership;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract_image")
public class ContractImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "link", length = Integer.MAX_VALUE)
    private String link;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted = false;
    @Column(name = "property_id")
    private Long propertyId;
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "property_id",
            referencedColumnName = "property_id",
            nullable = false,
            insertable = false,
            updatable = false)
    @JoinColumn(name = "user_id",
            referencedColumnName = "user_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Ownership ownership;
}