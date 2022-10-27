package com.xm.recommendation.web.handler;

import com.xm.recommendation.service.CryptocurrencyPriceService;
import com.xm.recommendation.web.dto.CryptocurrencyStatsDto;
import com.xm.recommendation.web.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetCryptocurrencyStatsRequestHandlerImpl implements GetCryptocurrencyStatsRequestHandler {

    private final CryptocurrencyPriceService cryptocurrencyPriceService;

    @Override
    public Mono<ServerResponse> getCryptocurrencyStats(ServerRequest serverRequest) {
        String cryptocurrencyName = serverRequest.pathVariable("name");
        return Mono.just(new CryptocurrencyStatsDto())
                .map(cryptoStatsDto -> {
                    cryptoStatsDto.setName(cryptocurrencyName);
                    return cryptoStatsDto;
                })
                .flatMap(cryptoStatsDto -> cryptocurrencyPriceService.getHighestPrice(cryptocurrencyName)
                        .map(price -> {
                            cryptoStatsDto.setHighestPrice(price);
                            return cryptoStatsDto;
                        }))
                .flatMap(cryptoStatsDto -> cryptocurrencyPriceService.getLowestPrice(cryptocurrencyName)
                        .map(price -> {
                            cryptoStatsDto.setLowestPrice(price);
                            return cryptoStatsDto;
                        }))
                .flatMap(cryptoStatsDto -> cryptocurrencyPriceService.getOldestPrice(cryptocurrencyName)
                        .map(price -> {
                            cryptoStatsDto.setOldestPrice(price);
                            return cryptoStatsDto;
                        }))
                .flatMap(cryptoStatsDto -> cryptocurrencyPriceService.getNewestPrice(cryptocurrencyName)
                        .map(price -> {
                            cryptoStatsDto.setNewestPrice(price);
                            return cryptoStatsDto;
                        }))
                .flatMap(RequestUtils::buildOkResponse)
                .switchIfEmpty(RequestUtils.buildNotFoundResponse());
    }

}
