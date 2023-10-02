package com.example.holidayswap.domain.entity.subscription;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "plan_id"
    )
    private Long planId;
    @Column(
            name = "plan_name",
            nullable = false,
            unique = true
    )
    private String planName;
    @Column
    private String description;
    @Column
    private String image;
    @Column(
            name = "price",
            nullable = false
    )
    private double price;
    @NotNull(message = "Price type must be specified.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriceType priceType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanPriceInterval planPriceInterval;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime lastModifiedOn;
    @Column(
            name = "is_active",
            nullable = false
    )
    private boolean isActive;
    @OneToMany(mappedBy = "plan",
            orphanRemoval = true
    )
    private List<Subscription> subscriptions;
}
