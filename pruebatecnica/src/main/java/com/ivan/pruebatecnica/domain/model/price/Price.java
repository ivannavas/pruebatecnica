package com.ivan.pruebatecnica.domain.model.price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonView;
import com.ivan.pruebatecnica.domain.model.brand.Brand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents the price entity in the e-commerce database.
 */
@Data
@Entity
@Table(name = "PRICES")
public class Price {
    @Id
    @Column(name = "PRICE_ID", nullable = false)
    private Long priceId; // Unique identifier for the price entry.

    @ManyToOne
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "BRAND_ID", nullable = false)
    @JsonView(PriceViews.GetBrandProductPriceResponse.class)
    private Brand brand; // Brand associated with the price entry.

    @Column(name = "START_DATE", nullable = false)
    @JsonView(PriceViews.GetBrandProductPriceResponse.class)
    private LocalDateTime startDate; // Start date of the price validity.

    @Column(name = "END_DATE", nullable = false)
    @JsonView(PriceViews.GetBrandProductPriceResponse.class)
    private LocalDateTime endDate; // End date of the price validity.

    @Column(name = "PRICE_LIST", nullable = false)
    @JsonView(PriceViews.GetBrandProductPriceResponse.class)
    private Long priceList; // Identifier of the price list.

    @Column(name = "PRODUCT_ID", nullable = false)
    @JsonView(PriceViews.GetBrandProductPriceResponse.class)
    private Long productId; // Identifier of the product.

    @Column(name = "PRIORITY", nullable = false)
    private Integer priority; // Priority for price application when multiple prices overlap.

    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    @JsonView(PriceViews.GetBrandProductPriceResponse.class)
    private BigDecimal price; // Final sale price.

    @Column(name = "CURR", nullable = false, length = 3)
    private String currency; // ISO code of the currency.
}
