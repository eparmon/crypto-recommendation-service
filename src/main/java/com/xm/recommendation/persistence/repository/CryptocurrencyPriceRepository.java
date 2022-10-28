package com.xm.recommendation.persistence.repository;

import com.xm.recommendation.persistence.domain.CryptocurrencyPrice;
import com.xm.recommendation.persistence.dto.CryptocurrencyWithNormalizedRange;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface CryptocurrencyPriceRepository extends R2dbcRepository<CryptocurrencyPrice, Long> {

    @Query("""
            SELECT cryptocurrency, ((MAX(value_usd) - MIN(value_usd)) / MIN(value_usd)) AS normalized_range
            FROM cryptocurrency_prices
            GROUP BY cryptocurrency
            """)
    Flux<CryptocurrencyWithNormalizedRange> getCryptocurrenciesWithNormalizedRange();

    @Query("""
            SELECT c.cryptocurrency
            FROM (
                SELECT cryptocurrency, ((MAX(value_usd) - MIN(value_usd)) / MIN(value_usd)) AS normalized_range
                FROM cryptocurrency_prices
                WHERE `timestamp` >= :timestampFrom AND `timestamp` < :timestampTo
                GROUP BY cryptocurrency) AS c
            ORDER BY c.normalized_range DESC
            LIMIT 1
            """)
    Mono<String> getCryptocurrencyWithHighestNormalizedRangeBetweenTimestamps(Long timestampFrom, Long timestampTo);

    @Query("""
            SELECT value_usd
            FROM cryptocurrency_prices
            WHERE cryptocurrency = :cryptocurrency
            ORDER BY `timestamp`
            LIMIT 1
            """)
    Mono<BigDecimal> getOldestPriceByCryptocurrency(String cryptocurrency);

    @Query("""
            SELECT value_usd
            FROM cryptocurrency_prices
            WHERE cryptocurrency = :cryptocurrency
            ORDER BY `timestamp` DESC
            LIMIT 1
            """)
    Mono<BigDecimal> getNewestPriceByCryptocurrency(String cryptocurrency);

    @Query("""
            SELECT value_usd
            FROM cryptocurrency_prices
            WHERE cryptocurrency = :cryptocurrency
            ORDER BY value_usd
            LIMIT 1
            """)
    Mono<BigDecimal> getLowestPriceByCryptocurrency(String cryptocurrency);

    @Query("""
            SELECT value_usd
            FROM cryptocurrency_prices
            WHERE cryptocurrency = :cryptocurrency
            ORDER BY value_usd DESC
            LIMIT 1
            """)
    Mono<BigDecimal> getHighestPriceByCryptocurrency(String cryptocurrency);

}
