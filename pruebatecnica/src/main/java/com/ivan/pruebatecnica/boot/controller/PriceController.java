package com.ivan.pruebatecnica.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.ivan.pruebatecnica.application.service.PriceService;
import com.ivan.pruebatecnica.domain.model.price.Price;
import com.ivan.pruebatecnica.domain.model.price.PriceSearch;
import com.ivan.pruebatecnica.domain.model.price.PriceViews;


/**
 * Controller class for managing price-related operations.
 */
@RestController
@RequestMapping("/api/price")
public class PriceController {

	@Autowired
	private PriceService priceService;

	/**
	 * Retrieves the price information for a specific brand and product based on the provided params.
	 *
	 * @param priceSearch a {@link PriceSearch}.
	 * @return A {@link Price} with all the information about the price, product, taxes and application dates.
	 *
	 */
	@GetMapping("/get-brand-product-price")
	public @JsonView(PriceViews.GetBrandProductPriceResponse.class) Price getBrandProductPrice(
			@ModelAttribute @JsonView(PriceViews.GetBrandProductPriceRequest.class) PriceSearch priceSearch) {
		return priceService.getBrandProductPrice(priceSearch);
	}
}
