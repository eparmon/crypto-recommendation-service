package com.xm.recommendation.web.handler;

import com.xm.recommendation.persistence.domain.CryptocurrencyPrice;
import com.xm.recommendation.persistence.repository.CryptocurrencyPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureWebTestClient
class GetCryptocurrenciesRequestHandlerImplTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CryptocurrencyPriceRepository cryptocurrencyPriceRepository;

    @BeforeEach
    void setUp() {
        cryptocurrencyPriceRepository.deleteAll().block();
    }

    @Test
    void getCryptocurrenciesSortedByNormalizedRange() {
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
                .blockLast();

        webTestClient.get()
                .uri("/cryptocurrencies")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {
                })
                .isEqualTo(List.of("ETH", "LTC", "BTC"));

    }

    @Test
    void getCryptocurrenciesSortedByNormalizedRangeWithNoData() {
        webTestClient.get()
                .uri("/cryptocurrencies")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {
                })
                .isEqualTo(List.of());
    }

}
