package com.xm.recommendation.service;

import com.xm.recommendation.persistence.dto.CryptocurrencyWithNormalizedRange;
import com.xm.recommendation.persistence.repository.CryptocurrencyPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptocurrencyPriceServiceImpl implements CryptocurrencyPriceService {

    private final CryptocurrencyPriceRepository cryptocurrencyPriceRepository;

    @Override
    public Mono<List<String>> getCryptocurrenciesSortedByNormalizedRange() {
        return cryptocurrencyPriceRepository.getCryptocurrenciesWithNormalizedRange()
                .collectList()
                .map(list -> list.stream()
                        .sorted(Comparator.comparing(CryptocurrencyWithNormalizedRange::getNormalizedRange).reversed())
                        .map(CryptocurrencyWithNormalizedRange::getCryptocurrency)
                        .toList());
    }

    @Override
    public Mono<String> getCryptocurrencyWithHighestNormalizedRange(LocalDate localDate) {
        long timestampFrom = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000;
        long timestampTo = localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
                .getEpochSecond() * 1000;
        return cryptocurrencyPriceRepository.getCryptocurrencyWithHighestNormalizedRangeBetweenTimestamps(timestampFrom, timestampTo);
    }

    @Override
    public Mono<BigDecimal> getOldestPrice(String cryptocurrency) {
        return cryptocurrencyPriceRepository.getOldestPriceByCryptocurrency(cryptocurrency);
    }

    @Override
    public Mono<BigDecimal> getNewestPrice(String cryptocurrency) {
        return cryptocurrencyPriceRepository.getNewestPriceByCryptocurrency(cryptocurrency);
    }

    @Override
    public Mono<BigDecimal> getLowestPrice(String cryptocurrency) {
        return cryptocurrencyPriceRepository.getLowestPriceByCryptocurrency(cryptocurrency);
    }

    @Override
    public Mono<BigDecimal> getHighestPrice(String cryptocurrency) {
        return cryptocurrencyPriceRepository.getHighestPriceByCryptocurrency(cryptocurrency);
    }

}
