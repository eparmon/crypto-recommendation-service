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
        loadTestData();

        StepVerifier.create(cryptocurrencyPriceRepository.getCryptocurrenciesWithNormalizedRange())
                .expectNext(new CryptocurrencyWithNormalizedRange("BTC", new BigDecimal("0.500000")))
                .expectNext(new CryptocurrencyWithNormalizedRange("ETH", new BigDecimal("12.500000")))
                .expectNext(new CryptocurrencyWithNormalizedRange("LTC", new BigDecimal("1.000000")))
                .expectComplete()
                .verify();
    }

    @Test
    void getCryptocurrenciesWithNormalizedRangeWithNoData() {
        StepVerifier.create(cryptocurrencyPriceRepository.getCryptocurrenciesWithNormalizedRange())
                .expectComplete()
                .verify();
    }

    @Test
    void getCryptocurrencyWithHighestNormalizedRangeBetweenTimestamps() {
        loadTestData();

        StepVerifier.create(cryptocurrencyPriceRepository.getCryptocurrencyWithHighestNormalizedRangeBetweenTimestamps(
                1641000000000L, 1641200000000L))
                .expectNext("ETH")
                .expectComplete()
                .verify();
    }

    @Test
    void getCryptocurrencyWithHighestNormalizedRangeBetweenTimestampsWithNoData() {
        loadTestData();

        StepVerifier.create(cryptocurrencyPriceRepository.getCryptocurrencyWithHighestNormalizedRangeBetweenTimestamps(
                1643000000000L, 1641400000000L))
                .expectComplete()
                .verify();
    }

    @Test
    void getOldestPriceByCryptocurrency() {
        loadTestData();

        StepVerifier.create(cryptocurrencyPriceRepository.getOldestPriceByCryptocurrency("ETH"))
                .expectNext(new BigDecimal("200.00"))
                .expectComplete()
                .verify();
    }

    @Test
    void getNewestPriceByCryptocurrency() {
        loadTestData();

        StepVerifier.create(cryptocurrencyPriceRepository.getNewestPriceByCryptocurrency("ETH"))
                .expectNext(new BigDecimal("405.00"))
                .expectComplete()
                .verify();
    }

    @Test
    void getHighestPriceByCryptocurrency() {
        loadTestData();

        StepVerifier.create(cryptocurrencyPriceRepository.getHighestPriceByCryptocurrency("BTC"))
                .expectNext(new BigDecimal("30.00"))
                .expectComplete()
                .verify();
    }

    @Test
    void getLowestPriceByCryptocurrency() {
        loadTestData();

        StepVerifier.create(cryptocurrencyPriceRepository.getLowestPriceByCryptocurrency("LTC"))
                .expectNext(new BigDecimal("1.00"))
                .expectComplete()
                .verify();
    }

    private void loadTestData() {
        cryptocurrencyPriceRepository.saveAll(List.of(
                        // 01.01.2022
                        new CryptocurrencyPrice(null, "BTC", 1641000000000L, new BigDecimal(20)),
                        new CryptocurrencyPrice(null, "ETH", 1641000000000L, new BigDecimal(200)),
                        new CryptocurrencyPrice(null, "LTC", 1641000000000L, new BigDecimal(2)),
                        new CryptocurrencyPrice(null, "BTC", 1641000010000L, new BigDecimal(30)),
                        new CryptocurrencyPrice(null, "ETH", 1641000010000L, new BigDecimal(30)),
                        new CryptocurrencyPrice(null, "LTC", 1641000010000L, new BigDecimal(1)),
                        // 03.01.2022
                        new CryptocurrencyPrice(null, "BTC", 1641200000000L, new BigDecimal(25)),
                        new CryptocurrencyPrice(null, "ETH", 1641200000000L, new BigDecimal(100)),
                        new CryptocurrencyPrice(null, "ETH", 1641200010000L, new BigDecimal(405))))
                .blockLast();
    }

}
