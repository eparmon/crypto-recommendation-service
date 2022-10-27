package com.xm.recommendation.service;

import com.xm.recommendation.persistence.dto.CryptocurrencyWithNormalizedRange;
import com.xm.recommendation.persistence.repository.CryptocurrencyPriceRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CryptocurrencyPriceServiceImplTest {

    private final CryptocurrencyPriceRepository cryptocurrencyPriceRepository = mock(CryptocurrencyPriceRepository.class);

    @Test
    void getCryptocurrenciesSortedByNormalizedRange() {
        CryptocurrencyPriceService service = new CryptocurrencyPriceServiceImpl(cryptocurrencyPriceRepository);
        when(cryptocurrencyPriceRepository.getCryptocurrenciesWithNormalizedRange())
                .thenReturn(Flux.just(
                        new CryptocurrencyWithNormalizedRange("BTC", new BigDecimal("0.200000")),
                        new CryptocurrencyWithNormalizedRange("DOGE", new BigDecimal("0.550000")),
                        new CryptocurrencyWithNormalizedRange("ETH", new BigDecimal("0.250000")),
                        new CryptocurrencyWithNormalizedRange("LTC", new BigDecimal("0.100000")),
                        new CryptocurrencyWithNormalizedRange("XRP", new BigDecimal("0.450000"))));

        StepVerifier.create(service.getCryptocurrenciesSortedByNormalizedRange())
                .expectNext(List.of("DOGE", "XRP", "ETH", "BTC", "LTC"))
                .expectComplete()
                .verify();
    }

    @Test
    void getCryptocurrenciesSortedByNormalizedRangeWithNoData() {
        CryptocurrencyPriceService service = new CryptocurrencyPriceServiceImpl(cryptocurrencyPriceRepository);
        when(cryptocurrencyPriceRepository.getCryptocurrenciesWithNormalizedRange())
                .thenReturn(Flux.empty());

        StepVerifier.create(service.getCryptocurrenciesSortedByNormalizedRange())
                .expectNext(List.of())
                .expectComplete()
                .verify();
    }

}