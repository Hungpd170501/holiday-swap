package com.example.holidayswap.domain.entity.subscription;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table
public class SubscriptionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "subscription_detail_id"
    )
    private Long subscriptionDetailId;
    @Column(
            name = "plan_id",
            nullable = false
    )
    private Long planId;
    private double price;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @Column
    private LocalDateTime periodStart;
    @Column
    private LocalDateTime periodEnd;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id",
            referencedColumnName = "subscription_id")
    private Subscription subscription;
}
