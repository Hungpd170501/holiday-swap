package com.example.holidayswap.domain.entity.resort;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long resortId;
    @Column(name = "link", length = Integer.MAX_VALUE)
    @NotNull
    private String link;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "resort_id",
            referencedColumnName = "resort_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Resort resort;

}