package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "property_view", schema = "public")
public class PropertyView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_view_id", nullable = false)
    private Long id;
    @NotNull
    @Length(min = 5, message = "Name length must greater than 5")
    @Column(name = "property_view_name", length = Integer.MAX_VALUE)
    private String propertyViewName;
    @Column(name = "property_view_description", length = Integer.MAX_VALUE)
    private String propertyViewDescription;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

}
