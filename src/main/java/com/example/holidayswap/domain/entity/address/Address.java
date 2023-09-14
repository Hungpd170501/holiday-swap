package com.example.holidayswap.domain.entity.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address", schema = "public")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long id;

    @Size(max = 35)
    @NotNull
    @Column(name = "code", nullable = false, length = 35)
    private String code;

    @Column(name = "coordinates", columnDefinition = "point(0, 0)")
    private Object coordinates;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "formatted_name")
    private String formattedName;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

}