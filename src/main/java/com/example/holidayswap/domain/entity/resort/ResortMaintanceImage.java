package com.example.holidayswap.domain.entity.resort;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resort_maintaince_image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResortMaintanceImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintaince_image_id")
    private Long id;

//    @Column(name = "maintaince_id", nullable = false)
//    private Long maintainceId;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "maintaince_id", nullable = false)
    private ResortMaintance resortMaintance;
}
