package com.xm.recommendation.web.handler;

import com.xm.recommendation.service.CryptocurrencyPriceService;
import com.xm.recommendation.web.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetCryptocurrenciesRequestHandlerImpl implements GetCryptocurrenciesRequestHandler {

    private final CryptocurrencyPriceService cryptocurrencyPriceService;

    @Override
    public Mono<ServerResponse> getCryptocurrenciesSortedByNormalizedRange(ServerRequest serverRequest) {
        return cryptocurrencyPriceService.getCryptocurrenciesSortedByNormalizedRange()
                .flatMap(RequestUtils::buildOkResponse);
    }

}
