package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "contract_image_id", nullable = false)
    private Long id;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "link", length = Integer.MAX_VALUE)
    private String link;

    @Column(name = "ownership_id")
    private Long ownershipId;

    @OneToMany(mappedBy = "contractImage")
    private Set<Ownership> ownerships = new LinkedHashSet<>();
}