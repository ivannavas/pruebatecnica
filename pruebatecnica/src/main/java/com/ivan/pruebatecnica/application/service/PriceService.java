package com.ivan.pruebatecnica.application.service;

import com.ivan.pruebatecnica.domain.model.price.Price;
import com.ivan.pruebatecnica.domain.model.price.PriceSearch;

/**
 * Service interface for managing price-related operations.
 */
public interface PriceService {
    /**
     * Retrieves the price information for a specific brand and product based on the provided params.
     *
     * @param priceSearch a {@link PriceSearch}.
	 * @return A {@link Price} with all the information about the price, product, taxes and application dates.
     */
    Price getBrandProductPrice(PriceSearch priceSearch);
}
