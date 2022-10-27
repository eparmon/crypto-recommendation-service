package com.xm.recommendation.web.config;

import com.xm.recommendation.web.handler.GetCryptocurrenciesRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class WebConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(GetCryptocurrenciesRequestHandler getCryptocurrenciesRequestHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/cryptocurrencies"),
                getCryptocurrenciesRequestHandler::getCryptocurrenciesSortedByNormalizedRange);
    }

}