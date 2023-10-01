package com.example.holidayswap.domain.entity.property.ownership;

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
public class OwnershipId implements Serializable {
    private static final long serialVersionUID = -4184519122503267383L;

    @NotNull
    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
