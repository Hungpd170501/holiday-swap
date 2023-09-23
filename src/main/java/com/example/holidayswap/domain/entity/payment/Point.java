package com.example.holidayswap.domain.entity.payment;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "point")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "point_id"
    )
    private Long id;

    @Column(name = "point_price")
    private Double pointPrice;

    @Column(name = "point_created_date")
    private String pointCreatedDate;

    @Column(name = "point_status")
    private EnumPaymentStatus.StatusPoint pointStatus;

}
