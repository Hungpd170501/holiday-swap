package com.example.holidayswap.domain.entity.address;

import com.example.holidayswap.domain.entity.common.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 450)
    private String name;

    @Column
    private String code;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<StateOrProvince> stateOrProvinces;
}