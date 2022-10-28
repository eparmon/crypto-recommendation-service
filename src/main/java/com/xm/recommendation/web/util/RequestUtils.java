package com.xm.recommendation.web.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class RequestUtils {

    private RequestUtils() {
    }

    public static <T> Mono<ServerResponse> buildOkResponse(T body, MediaType mediaType) {
        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(mediaType)
                .bodyValue(body);
    }

    public static Mono<ServerResponse> buildNotFoundResponse() {
        return ServerResponse
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    public static Mono<ServerResponse> buildBadRequestResponse(String errorMessage) {
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue(errorMessage);
    }


    public static String extractRequiredQueryParam(ServerRequest request, String name) {
        return request.queryParam(name)
                .orElseThrow(() -> new IllegalStateException("Param " + name + " was not provided"));
    }

}
