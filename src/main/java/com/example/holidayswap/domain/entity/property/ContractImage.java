package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.LinkedHashSet;
import java.util.Set;

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

//    @EmbeddedId
//    private OwnershipId ownershipId;

    private Long propertyId;
    private Long userId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "link", length = Integer.MAX_VALUE)
    private String link;
 
    @ManyToOne
    private Ownership ownership;
}