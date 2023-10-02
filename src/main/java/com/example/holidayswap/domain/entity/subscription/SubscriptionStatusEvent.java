package com.example.holidayswap.domain.entity.subscription;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class SubscriptionStatusEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "subscription_status_event_id"
    )
    private Long subscriptionStatusEventId;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @NotNull(message = "Subscription status event must be specified.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionEventType subscriptionEventType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
}
