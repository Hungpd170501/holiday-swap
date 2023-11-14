package com.example.holidayswap.domain.entity.property.rating;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RatingId implements Serializable {
    private static final long serialVersionUID = -4184519122503267383L;
    @NotNull
    @Column(name = "available_time_id", nullable = false)
    private Long availableTimeId;
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
