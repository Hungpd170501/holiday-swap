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
public class CronJobLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "cron_job_log_id"
    )
    private Long cronJobLogId;
    @Column(
            name = "cron_job_name",
            nullable = false
    )
    private String cronJobName;
    @NotNull(message = "Cron job status must be specified.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CronJobStatus cronJobStatus;
    @Column(
            name = "message",
            nullable = false
    )
    private String message;
    @CreationTimestamp
    private LocalDateTime createdOn;
}
