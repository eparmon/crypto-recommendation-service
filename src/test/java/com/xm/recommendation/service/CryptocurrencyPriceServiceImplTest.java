package com.xm.recommendation.service;

import com.xm.recommendation.persistence.dto.CryptocurrencyWithNormalizedRange;
import com.xm.recommendation.persistence.repository.CryptocurrencyPriceRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CryptocurrencyPriceServiceImplTest {

    private final CryptocurrencyPriceRepository cryptocurrencyPriceRepository = mock(CryptocurrencyPriceRepository.class);
    private final CryptocurrencyPriceService service = new CryptocurrencyPriceServiceImpl(cryptocurrencyPriceRepository);

    @Test
    void getCryptocurrenciesSortedByNormalizedRange() {
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
        when(cryptocurrencyPriceRepository.getCryptocurrenciesWithNormalizedRange())
                .thenReturn(Flux.empty());

        StepVerifier.create(service.getCryptocurrenciesSortedByNormalizedRange())
                .expectNext(List.of())
                .expectComplete()
                .verify();
    }

    @Test
    void getCryptocurrencyWithHighestNormalizedRange() {
        LocalDate date = LocalDate.of(2022, 1, 1);
        long timestampFrom = date.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000;
        LocalDate nextDate = LocalDate.of(2022, 1, 2);
        long timestampTo = nextDate.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000;
        when(cryptocurrencyPriceRepository.getCryptocurrencyWithHighestNormalizedRangeBetweenTimestamps(
                timestampFrom, timestampTo)).thenReturn(Mono.just("BTC"));

        StepVerifier.create(service.getCryptocurrencyWithHighestNormalizedRange(date))
                .expectNext("BTC")
                .expectComplete()
                .verify();
    }

    @Test
    void getCryptocurrencyWithHighestNormalizedRangeWithNoData() {
        LocalDate date = LocalDate.of(2022, 1, 1);
        long timestampFrom = date.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000;
        LocalDate nextDate = LocalDate.of(2022, 1, 2);
        long timestampTo = nextDate.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000;
        when(cryptocurrencyPriceRepository.getCryptocurrencyWithHighestNormalizedRangeBetweenTimestamps(
                timestampFrom, timestampTo)).thenReturn(Mono.empty());

        StepVerifier.create(service.getCryptocurrencyWithHighestNormalizedRange(date))
                .expectComplete()
                .verify();
    }

    @Test
    void getOldestPrice() {
        when(cryptocurrencyPriceRepository.getOldestPriceByCryptocurrency("BTC"))
                .thenReturn(Mono.just(new BigDecimal("20.00")));
        StepVerifier.create(service.getOldestPrice("BTC"))
                .expectNext(new BigDecimal("20.00"))
                .expectComplete()
                .verify();
    }

    @Test
    void getNewestPrice() {
        when(cryptocurrencyPriceRepository.getNewestPriceByCryptocurrency("BTC"))
                .thenReturn(Mono.just(new BigDecimal("30.00")));
        StepVerifier.create(service.getNewestPrice("BTC"))
                .expectNext(new BigDecimal("30.00"))
                .expectComplete()
                .verify();
    }
    @Test
    void getLowestPrice() {
        when(cryptocurrencyPriceRepository.getLowestPriceByCryptocurrency("BTC"))
                .thenReturn(Mono.just(new BigDecimal("40.00")));
        StepVerifier.create(service.getLowestPrice("BTC"))
                .expectNext(new BigDecimal("40.00"))
                .expectComplete()
                .verify();
    }
    @Test
    void getHighestPrice() {
        when(cryptocurrencyPriceRepository.getHighestPriceByCryptocurrency("BTC"))
                .thenReturn(Mono.just(new BigDecimal("50.00")));
        StepVerifier.create(service.getHighestPrice("BTC"))
                .expectNext(new BigDecimal("50.00"))
                .expectComplete()
                .verify();
    }


}
