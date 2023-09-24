package com.ivan.pruebatecnica.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivan.pruebatecnica.application.service.PriceService;
import com.ivan.pruebatecnica.domain.model.price.Price;
import com.ivan.pruebatecnica.domain.model.price.PriceSearch;
import com.ivan.pruebatecnica.domain.repository.PriceRepository;

@Service
public class PriceServiceImpl implements PriceService {
	@Autowired
	private PriceRepository priceService;

    /**
     * {@inheritDoc}
     */
	@Override
	public Price getBrandProductPrice(PriceSearch priceSearch) {
		return priceService.findProductPriceByParams(priceSearch);
	}
	
}
