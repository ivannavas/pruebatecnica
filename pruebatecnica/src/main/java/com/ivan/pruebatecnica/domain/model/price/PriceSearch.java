package com.ivan.pruebatecnica.domain.model.price;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * Represents the search criteria for retrieving price information.
 */
@Data
public class PriceSearch {
	@JsonView(PriceViews.GetBrandProductPriceRequest.class)
	private Long productId; // Identifier of the product.

	@JsonView(PriceViews.GetBrandProductPriceRequest.class)
	private LocalDateTime applicationDate; // Date of price application.

	@JsonView(PriceViews.GetBrandProductPriceRequest.class)
	private Long brandId; // Identifier of the brand.
}
