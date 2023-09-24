package com.ivan.pruebatecnica.domain.repository;

import com.ivan.pruebatecnica.domain.model.price.Price;
import com.ivan.pruebatecnica.domain.model.price.PriceSearch;

/**
 * Repository interface for querying price information.
 */
public interface PriceRepository {
    /**
     * Finds the price information for the provided params
     *
     * @param priceSearch a {@link PriceSearch}.
	 * @return A {@link Price}
     */
    Price findProductPriceByParams(PriceSearch priceSearch);
}
