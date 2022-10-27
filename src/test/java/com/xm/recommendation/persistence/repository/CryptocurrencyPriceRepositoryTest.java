package com.xm.recommendation.persistence.repository;

import com.xm.recommendation.persistence.domain.CryptocurrencyPrice;
import com.xm.recommendation.persistence.dto.CryptocurrencyWithNormalizedRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

@DataR2dbcTest
class CryptocurrencyPriceRepositoryTest {

    @Autowired
    private CryptocurrencyPriceRepository cryptocurrencyPriceRepository;

    @BeforeEach
    void setUp() {
        cryptocurrencyPriceRepository.deleteAll().block();
    }

    @Test
    void getCryptocurrenciesWithNormalizedRange() {
        cryptocurrencyPriceRepository.saveAll(List.of(
                        new CryptocurrencyPrice(null, "BTC", 1L, new BigDecimal(20)),
                        new CryptocurrencyPrice(null, "BTC", 2L, new BigDecimal(30)),
                        new CryptocurrencyPrice(null, "BTC", 3L, new BigDecimal(25)),
                        new CryptocurrencyPrice(null, "ETH", 1L, new BigDecimal(200)),
                        new CryptocurrencyPrice(null, "ETH", 2L, new BigDecimal(30)),
                        new CryptocurrencyPrice(null, "ETH", 3L, new BigDecimal(100)),
                        new CryptocurrencyPrice(null, "ETH", 4L, new BigDecimal(405)),
                        new CryptocurrencyPrice(null, "LTC", 1L, new BigDecimal(2)),
                        new CryptocurrencyPrice(null, "LTC", 2L, new BigDecimal(1))))
                .collectList()
                .block();

        StepVerifier.create(cryptocurrencyPriceRepository.getCryptocurrenciesWithNormalizedRange())
                .expectNext(new CryptocurrencyWithNormalizedRange("BTC", new BigDecimal("0.500000")))
                .expectNext(new CryptocurrencyWithNormalizedRange("ETH", new BigDecimal("12.500000")))
                .expectNext(new CryptocurrencyWithNormalizedRange("LTC", new BigDecimal("1.000000")))
                .expectComplete()
                .verify();
    }

}
