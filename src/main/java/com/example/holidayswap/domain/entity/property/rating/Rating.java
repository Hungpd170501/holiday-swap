package com.example.holidayswap.domain.entity.property.rating;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.Property;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rating", schema = "public")
public class Rating {
    @EmbeddedId
    private RatingId id;
    @MapsId("propertyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String comment;
    @NotNull
    @PositiveOrZero
    private double rating;
}
