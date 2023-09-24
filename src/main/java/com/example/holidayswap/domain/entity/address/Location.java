package com.example.holidayswap.domain.entity.address;

import com.example.holidayswap.domain.entity.resort.Resort;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "location", schema = "public")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, length = 35)
    private String code;

    @Column(name = "coordinates", columnDefinition = "Point")
    private Point coordinates;

    @Column(name = "description")
    private String description;

    @Column(name = "formatted_name")
    private String formattedName;

    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "location")
    private List<Resort> resorts;
}