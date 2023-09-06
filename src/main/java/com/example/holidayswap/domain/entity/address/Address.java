package com.example.holidayswap.domain.entity.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(nullable = false)
    private String name;

    @Column(name = "formatted_name")
    private String formattedName;

    @Column
    private String description;

    @Column(nullable = false, length = 35)
    private String code;

    @Column(name = "coordinates", columnDefinition = "Point")
    private Point coordinates;
}
