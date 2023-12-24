package com.example.holidayswap.domain.entity.property.coOwner;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted = false;
    @Column(name = "co_owner_id")
    @NotNull
    private Long coOwnerId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "co_owner_id", referencedColumnName = "co_owner_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private CoOwner coOwner;
}