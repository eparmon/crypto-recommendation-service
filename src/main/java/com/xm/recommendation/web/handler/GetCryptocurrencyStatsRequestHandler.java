package com.xm.recommendation.web.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface GetCryptocurrencyStatsRequestHandler {

    Mono<ServerResponse> getCryptocurrencyStats(ServerRequest serverRequest);

}
