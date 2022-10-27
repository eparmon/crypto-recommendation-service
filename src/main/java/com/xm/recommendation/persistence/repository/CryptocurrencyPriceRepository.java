package com.xm.recommendation.persistence.repository;

import com.xm.recommendation.persistence.domain.CryptocurrencyPrice;
import com.xm.recommendation.persistence.dto.CryptocurrencyWithNormalizedRange;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CryptocurrencyPriceRepository extends R2dbcRepository<CryptocurrencyPrice, Long> {

    @Query("""
            SELECT cryptocurrency, ((MAX(value_usd) - MIN(value_usd)) / MIN(value_usd)) AS normalized_range
            FROM cryptocurrency_prices
            GROUP BY cryptocurrency
            """)
    Flux<CryptocurrencyWithNormalizedRange> getCryptocurrenciesWithNormalizedRange();

}
