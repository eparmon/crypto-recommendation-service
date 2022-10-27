package com.xm.recommendation.web.config;

import com.xm.recommendation.web.handler.GetCryptocurrenciesRequestHandler;
import com.xm.recommendation.web.handler.GetCryptocurrencyStatsRequestHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class WebConfig {

    @Bean
    @RouterOperation(
            beanClass = GetCryptocurrenciesRequestHandler.class,
            beanMethod = "getCryptocurrenciesSortedByNormalizedRange",
            produces = "application/json")
    public RouterFunction<ServerResponse> getCryptocurrencies(GetCryptocurrenciesRequestHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/cryptocurrencies"),
                handler::getCryptocurrenciesSortedByNormalizedRange);
    }

    @Bean
    @RouterOperation(
            beanClass = GetCryptocurrencyStatsRequestHandler.class,
            beanMethod = "getCryptocurrencyStats",
            produces = "application/json")
    public RouterFunction<ServerResponse> getCryptocurrencyStats(GetCryptocurrencyStatsRequestHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/cryptocurrencies/{name}/stats"),
                handler::getCryptocurrencyStats);
    }

}
