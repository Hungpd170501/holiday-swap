package com.example.holidayswap.domain.entity.property.coOwner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "owner_ship_maintaince_image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerShipMaintenanceImage {
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
    private OwnerShipMaintenance ownershipMaintenance;
}
