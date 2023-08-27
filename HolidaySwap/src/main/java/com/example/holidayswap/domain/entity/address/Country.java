package com.example.holidayswap.domain.entity.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "country_id"
    )
    private Long countryId;

    @Column
    private String name;

    @Column(nullable = false, name = "long_name", columnDefinition = "full text description or name of the address component as returned by the Geocoder")
    private String longName;

    @Column(nullable = false, name = "short_name")
    private String shortName;
}
