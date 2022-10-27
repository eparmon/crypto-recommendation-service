package com.xm.recommendation.web.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class RequestUtils {

    private RequestUtils() {
    }

    public static <T> Mono<ServerResponse> buildOkResponse(T body) {
        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body);
    }

}
