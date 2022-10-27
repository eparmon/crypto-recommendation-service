package com.xm.recommendation.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface CryptocurrencyPriceService {

    Mono<List<String>> getCryptocurrenciesSortedByNormalizedRange();

}
