package com.example.holidayswap.domain.entity.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract_image")
public class ContractImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_image_id", nullable = false)
    private Long id;

    @Column(name = "link", length = Integer.MAX_VALUE)
    private String link;
    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDeleted = false;
    @Column(name = "property_contract_id")
    private Long propertyContractId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "property_contract_id",
            referencedColumnName = "property_contract_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private PropertyContract propertyContract;

}