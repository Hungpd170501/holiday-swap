package com.example.holidayswap.domain.entity.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "resort_image")
public class ResortImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_resort_id", nullable = false)
    private Long id;

    @JsonIgnore
    @Column(name = "resort_id")
    private Long resortId;

    @Column(name = "link", length = Integer.MAX_VALUE)
    private String link;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "resort_id",
            referencedColumnName = "resort_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Resort resort;

}