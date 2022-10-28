package com.xm.recommendation.web.handler;

import com.xm.recommendation.persistence.domain.CryptocurrencyPrice;
import com.xm.recommendation.persistence.repository.CryptocurrencyPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureWebTestClient
class GetCryptocurrencyWithTheHighestNormalizedRangeForDayRequestHandlerImplTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CryptocurrencyPriceRepository cryptocurrencyPriceRepository;

    @BeforeEach
    void setUp() {
        cryptocurrencyPriceRepository.deleteAll()
                .thenMany(cryptocurrencyPriceRepository.saveAll(List.of(
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
                        new CryptocurrencyPrice(null, "ETH", 1641200010000L, new BigDecimal(405)))))
                .blockLast();

    }

    @Test
    void getCryptocurrencyWithTheHighestNormalizedRangeForDate() {
        webTestClient.get()
                .uri("/cryptocurrencies/with-highest-normalized-range?date=01.01.2022")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("ETH");
    }

    @Test
    void getCryptocurrencyWithTheHighestNormalizedRangeForDateInWrongFormat() {
        webTestClient.get()
                .uri("/cryptocurrencies/with-highest-normalized-range?date=01-01-2022")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Cannot parse date. Please, pass date in dd.MM.yyyy format");
    }

    @Test
    void getCryptocurrencyWithTheHighestNormalizedRangeForDateWithMissingParam() {
        webTestClient.get()
                .uri("/cryptocurrencies/with-highest-normalized-range")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Param date was not provided");
    }

    @Test
    void getCryptocurrencyWithTheHighestNormalizedRangeForDateWithNoData() {
        webTestClient.get()
                .uri("/cryptocurrencies/with-highest-normalized-range?date=05.01.2022")
                .exchange()
                .expectStatus().isNotFound();
    }

}
