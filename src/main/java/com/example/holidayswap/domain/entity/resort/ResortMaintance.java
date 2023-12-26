package com.example.holidayswap.domain.entity.resort;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "resort_maintaince")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResortMaintance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintaince_id")
    private Long id;
    @Column(name = "resort_id", nullable = false)
    private Long resortId;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ResortStatus type;
    @ManyToOne
    @JoinColumn(name = "resort_id", referencedColumnName = "resort_id", nullable = false,
            insertable = false,
            updatable = false)
    private Resort resort;
    @OneToMany(mappedBy = "resortMaintance", cascade = CascadeType.ALL)
    private Set<ResortMaintanceImage> resortMaintanceImage;
}
