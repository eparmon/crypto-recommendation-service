package com.xm.recommendation.web.handler;

import com.xm.recommendation.service.CryptocurrencyPriceService;
import com.xm.recommendation.web.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetCryptocurrenciesRequestHandlerImpl implements GetCryptocurrenciesRequestHandler {

    private final CryptocurrencyPriceService cryptocurrencyPriceService;

    @Override
    @Operation(summary = "Get list of cryptocurrencies, sorted by normalized range",
            description = "Get list of cryptocurrencies, descending sorted by normalized range which is defined as " +
                    "(max/min)/min",
            responses = @ApiResponse(description = "Example response", responseCode = "200",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject("""
                            [
                              "ETH",
                              "XRP",
                              "LTC",
                              "DOGE",
                              "BTC"
                            ]
                            """))))
    public Mono<ServerResponse> getCryptocurrenciesSortedByNormalizedRange(ServerRequest serverRequest) {
        return cryptocurrencyPriceService.getCryptocurrenciesSortedByNormalizedRange()
                .flatMap(body -> RequestUtils.buildOkResponse(body, MediaType.APPLICATION_JSON));
    }

}
