package com.ivan.pruebatecnica.domain.model.brand;

import com.fasterxml.jackson.annotation.JsonView;
import com.ivan.pruebatecnica.domain.model.price.PriceViews;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents a brand entity in the e-commerce database.
 */
@Data
@Entity
@Table(name = "BRANDS")
public class Brand {
    @Id
    @Column(name = "BRAND_ID", nullable = false)
    @JsonView(PriceViews.GetBrandProductPriceResponse.class)
    private Long brandId; // Unique identifier for the brand.

    @Column(name = "NAME", nullable = false)
    private String name; // Name of the brand.
}
