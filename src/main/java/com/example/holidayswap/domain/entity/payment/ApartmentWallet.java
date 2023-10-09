package com.example.holidayswap.domain.entity.payment;

import com.example.holidayswap.domain.entity.property.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "apartment_wallet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentWallet {
    @EmbeddedId
    private ApartmentWalletId id;
    @MapsId("propertyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    @Column(name = "total_point")
    private Double totalPoint;
    // TODO: update transaction for apartment wallet (retaler wallet -> apartment wallet -> owner wallet)
}
