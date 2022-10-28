package com.xm.recommendation.web.config;

import com.xm.recommendation.web.handler.GetCryptocurrenciesRequestHandler;
import com.xm.recommendation.web.handler.GetCryptocurrencyStatsRequestHandler;
import com.xm.recommendation.web.handler.GetCryptocurrencyWithTheHighestNormalizedRangeForDayRequestHandler;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    private final RateLimiterRegistry rateLimiterRegistry;

    @Bean
    @RouterOperation(
            beanClass = GetCryptocurrenciesRequestHandler.class,
            beanMethod = "getCryptocurrenciesSortedByNormalizedRange",
            produces = "application/json")
    public RouterFunction<ServerResponse> getCryptocurrencies(GetCryptocurrenciesRequestHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/cryptocurrencies"),
                serverRequest -> handler.getCryptocurrenciesSortedByNormalizedRange(serverRequest)
                        .transformDeferred(RateLimiterOperator.of(getRateLimiter(serverRequest))));
    }

    @Bean
    @RouterOperation(
            beanClass = GetCryptocurrencyStatsRequestHandler.class,
            beanMethod = "getCryptocurrencyStats",
            produces = "application/json")
    public RouterFunction<ServerResponse> getCryptocurrencyStats(GetCryptocurrencyStatsRequestHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/cryptocurrencies/{name}/stats"),
                serverRequest -> handler.getCryptocurrencyStats(serverRequest)
                        .transformDeferred(RateLimiterOperator.of(getRateLimiter(serverRequest))));
    }

    @Bean
    @RouterOperation(
            beanClass = GetCryptocurrencyWithTheHighestNormalizedRangeForDayRequestHandler.class,
            beanMethod = "getCryptocurrencyWithTheHighestNormalizedRangeForDate",
            produces = "text/plain")
    public RouterFunction<ServerResponse> getCryptocurrencyWithTheHighestNormalizedRangeForDay(
            GetCryptocurrencyWithTheHighestNormalizedRangeForDayRequestHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/cryptocurrencies/with-highest-normalized-range"),
                serverRequest -> handler.getCryptocurrencyWithTheHighestNormalizedRangeForDate(serverRequest)
                        .transformDeferred(RateLimiterOperator.of(getRateLimiter(serverRequest))));
    }

    private RateLimiter getRateLimiter(ServerRequest serverRequest) {
        return serverRequest.remoteAddress()
                .map(remoteAddress -> rateLimiterRegistry.rateLimiter(remoteAddress.getAddress().getHostAddress()))
                .orElseGet(() -> rateLimiterRegistry.rateLimiter("default"));
    }

}
