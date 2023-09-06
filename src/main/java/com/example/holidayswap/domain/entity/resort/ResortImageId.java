package com.example.holidayswap.domain.entity.resort;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ResortImageId implements Serializable {
    private static final long serialVersionUID = -6310013206066914169L;
    @NotNull
    @Column(name = "resort_id", nullable = false)
    private Long resortId;

    @NotNull
    @Column(name = "image_id", nullable = false)
    private Long imageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ResortImageId entity = (ResortImageId) o;
        return Objects.equals(this.imageId, entity.imageId) &&
                Objects.equals(this.resortId, entity.resortId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, resortId);
    }

}