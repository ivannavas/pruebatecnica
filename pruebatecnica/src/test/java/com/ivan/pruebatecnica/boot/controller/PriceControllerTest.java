package com.ivan.pruebatecnica.boot.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ivan.pruebatecnica.domain.model.brand.Brand;
import com.ivan.pruebatecnica.domain.model.price.Price;
import com.ivan.pruebatecnica.domain.model.price.PriceSearch;

/**
 * Price controller tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetBrandProductPriceDay14At10AM() throws Exception {
		testZaraProductPriceDataOK(LocalDateTime.of(2020, 6, 14, 10, 0));
	}

	@Test
	public void testGetBrandProductPriceDay14At9PM() throws Exception {
		testZaraProductPriceDataOK(LocalDateTime.of(2020, 6, 14, 21, 0));
	}

	@Test
	public void testGetBrandProductPriceDay14At4PM() throws Exception {
		testZaraProductPriceDataOK(LocalDateTime.of(2020, 6, 14, 16, 0));
	}

	@Test
	public void testGetBrandProductPriceDay15At10AM() throws Exception {
		testZaraProductPriceDataOK(LocalDateTime.of(2020, 6, 15, 10, 0));
	}

	@Test
	public void testGetBrandProductPriceDay16At9PM() throws Exception {
		testZaraProductPriceDataOK(LocalDateTime.of(2020, 6, 16, 21, 0));
	}

	@Test
	public void testGetBrandProductPriceKO() throws Exception {
		testZaraProductPriceDataKO(LocalDateTime.of(2022, 6, 15, 10, 0));
	}

	private void testZaraProductPriceDataOK(LocalDateTime date) throws Exception {
		var search = new PriceSearch();

		search.setApplicationDate(date);
		search.setProductId(35455L);
		search.setBrandId(1L);

		var expectedPrice = getExpectedPrice(search);

		assertNotNull(expectedPrice);

		var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		mockMvc.perform(get("/api/price/get-brand-product-price").param("brandId", String.valueOf(search.getBrandId()))
				.param("productId", String.valueOf(search.getProductId()))
				.param("applicationDate", search.getApplicationDate().format(formatter))
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price", equalTo(expectedPrice.getPrice().doubleValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.brand.brandId",
						equalTo(expectedPrice.getBrand().getBrandId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.startDate",
						equalTo(expectedPrice.getStartDate().format(formatter))))
				.andExpect(MockMvcResultMatchers.jsonPath("$.endDate",
						equalTo(expectedPrice.getEndDate().format(formatter))))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.priceList", equalTo(expectedPrice.getPriceList().intValue())))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.productId", equalTo(expectedPrice.getProductId().intValue())))
				.andExpect(status().isOk()).andReturn();
	}

	private void testZaraProductPriceDataKO(LocalDateTime date) throws Exception {
		var search = new PriceSearch();

		search.setApplicationDate(date);
		search.setProductId(35455L);
		search.setBrandId(1L);

		var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		assertNull(getExpectedPrice(search));

		mockMvc.perform(get("/api/price/get-brand-product-price").param("brandId", String.valueOf(search.getBrandId()))
				.param("productId", String.valueOf(search.getProductId()))
				.param("applicationDate", search.getApplicationDate().format(formatter))
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	/**
	 * Simulates the database logic and returns the expected price for the provided
	 * {@link PriceSearch}
	 */
	private Price getExpectedPrice(PriceSearch search) {
		return getDatabaseReplication().stream()
				.filter(price -> search.getBrandId().equals(price.getBrand().getBrandId())
						&& search.getProductId().equals(price.getProductId())
						&& search.getApplicationDate().isAfter(price.getStartDate())
						&& price.getEndDate().isAfter(search.getApplicationDate()))
				.sorted((p1, p2) -> {
					var priorityComparison = p2.getPriority().compareTo(p1.getPriority());
					if (priorityComparison == 0) {
						return p2.getStartDate().compareTo(p1.getStartDate());
					}

					return priorityComparison;
				}).findFirst().orElse(null);
	}

	private List<Price> getDatabaseReplication() {
		var databaseReplication = new ArrayList<Price>();

		var zara = new Brand();
		zara.setBrandId(1L);

		var price1 = new Price();
		price1.setPriceId(0L);
		price1.setBrand(zara);
		price1.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
		price1.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
		price1.setPriceList(1L);
		price1.setProductId(35455L);
		price1.setPriority(0);
		price1.setPrice(new BigDecimal("35.50"));
		price1.setCurrency("EUR");
		databaseReplication.add(price1);

		var price2 = new Price();
		price2.setPriceId(1L);
		price2.setBrand(zara);
		price2.setStartDate(LocalDateTime.parse("2020-06-14T15:00:00"));
		price2.setEndDate(LocalDateTime.parse("2020-06-14T18:30:00"));
		price2.setPriceList(2L);
		price2.setProductId(35455L);
		price2.setPriority(1);
		price2.setPrice(new BigDecimal("25.45"));
		price2.setCurrency("EUR");
		databaseReplication.add(price2);

		var price3 = new Price();
		price3.setPriceId(2L);
		price3.setBrand(zara);
		price3.setStartDate(LocalDateTime.parse("2020-06-15T00:00:00"));
		price3.setEndDate(LocalDateTime.parse("2020-06-15T11:00:00"));
		price3.setPriceList(3L);
		price3.setProductId(35455L);
		price3.setPriority(1);
		price3.setPrice(new BigDecimal("30.50"));
		price3.setCurrency("EUR");
		databaseReplication.add(price3);

		var price4 = new Price();
		price4.setPriceId(3L);
		price4.setBrand(zara);
		price4.setStartDate(LocalDateTime.parse("2020-06-15T16:00:00"));
		price4.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
		price4.setPriceList(4L);
		price4.setProductId(35455L);
		price4.setPriority(1);
		price4.setPrice(new BigDecimal("38.95"));
		price4.setCurrency("EUR");
		databaseReplication.add(price4);

		return databaseReplication;
	}
}
