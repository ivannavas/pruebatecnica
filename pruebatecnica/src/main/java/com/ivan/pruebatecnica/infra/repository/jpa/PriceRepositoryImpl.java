package com.ivan.pruebatecnica.infra.repository.jpa;

import org.springframework.stereotype.Repository;

import com.ivan.pruebatecnica.domain.model.price.Price;
import com.ivan.pruebatecnica.domain.model.price.PriceSearch;
import com.ivan.pruebatecnica.domain.repository.PriceRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Implementation of the PriceRepository interface using JPA
 */
@Repository
public class PriceRepositoryImpl implements PriceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    public Price findProductPriceByParams(PriceSearch priceSearch) {
        return entityManager
                .createQuery("SELECT p FROM Price p " + "WHERE p.brand.brandId = :brandId "
                        + "AND p.productId = :productId " + "AND :applicationDate BETWEEN p.startDate AND p.endDate "
                        + "ORDER BY p.priority DESC, p.startDate DESC", Price.class)
                .setParameter("brandId", priceSearch.getBrandId()).setParameter("productId", priceSearch.getProductId())
                .setParameter("applicationDate", priceSearch.getApplicationDate()).setMaxResults(1).getSingleResult();
    }

}
