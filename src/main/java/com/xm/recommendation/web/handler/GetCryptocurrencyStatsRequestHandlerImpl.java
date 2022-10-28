package com.xm.recommendation.web.handler;

import com.xm.recommendation.service.CryptocurrencyPriceService;
import com.xm.recommendation.web.dto.CryptocurrencyStatsDto;
import com.xm.recommendation.web.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetCryptocurrencyStatsRequestHandlerImpl implements GetCryptocurrencyStatsRequestHandler {

    private final CryptocurrencyPriceService cryptocurrencyPriceService;

    @Override
    @Operation(summary = "Get cryptocurrency price stats", description = "Get statistics for a given cryptocurrency, " +
            "including the lowest, the highest, the newest and the oldest values",
            parameters = @Parameter(name = "name", in = ParameterIn.PATH, description = "Cryptocurrency abbreviated name",
                    required = true, example = "BTC"),
            responses = {
                    @ApiResponse(description = "Example response", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CryptocurrencyStatsDto.class),
                                    examples = @ExampleObject("""
                                            {
                                                "name":"BTC",
                                                "oldestPrice":46813.21,
                                                "newestPrice":38415.79,
                                                "lowestPrice":33276.59,
                                                "highestPrice":47722.66
                                            }
                                            """))),
                    @ApiResponse(description = "Not found response, returned if there's no values for a given" +
                            " cryptocurrency", responseCode = "404")
            })
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
                .flatMap(body -> RequestUtils.buildOkResponse(body, MediaType.APPLICATION_JSON))
                .switchIfEmpty(RequestUtils.buildNotFoundResponse());
    }

}
