package com.xm.recommendation.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface CryptocurrencyPriceService {

    Mono<List<String>> getCryptocurrenciesSortedByNormalizedRange();

    Mono<String> getCryptocurrencyWithHighestNormalizedRange(LocalDate localDate);

    Mono<BigDecimal> getOldestPrice(String cryptocurrency);

    Mono<BigDecimal> getNewestPrice(String cryptocurrency);

    Mono<BigDecimal> getLowestPrice(String cryptocurrency);

    Mono<BigDecimal> getHighestPrice(String cryptocurrency);

}
