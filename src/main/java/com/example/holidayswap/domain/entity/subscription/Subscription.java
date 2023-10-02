package com.example.holidayswap.domain.entity.subscription;

import com.example.holidayswap.domain.entity.auth.User;
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
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "subscription_id"
    )
    private Long subscriptionId;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime lastModifiedOn;
    @Column
    private LocalDateTime currentPeriodStart;
    @Column
    private LocalDateTime currentPeriodEnd;
    @NotNull(message = "Subscription status must be specified.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus subscriptionStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id")
    private Plan plan;

    @OneToMany(mappedBy = "subscription",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<SubscriptionDetail> subscriptionDetails;

    @OneToMany(mappedBy = "subscription",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<SubscriptionStatusEvent> subscriptionStatusEvents;


}
