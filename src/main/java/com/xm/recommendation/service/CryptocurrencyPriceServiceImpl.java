package com.xm.recommendation.service;

import com.xm.recommendation.persistence.dto.CryptocurrencyWithNormalizedRange;
import com.xm.recommendation.persistence.repository.CryptocurrencyPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

}
