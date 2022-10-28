package com.xm.recommendation.web.handler;

import com.xm.recommendation.service.CryptocurrencyPriceService;
import com.xm.recommendation.web.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
public class GetCryptocurrencyWithTheHighestNormalizedRangeForDayRequestHandlerImpl
        implements GetCryptocurrencyWithTheHighestNormalizedRangeForDayRequestHandler {

    private final CryptocurrencyPriceService cryptocurrencyPriceService;

    @Override
    @Operation(summary = "Get cryptocurrency with the highest normalized range for date",
            description = "Get cryptocurrency with the highest normalized range for date",
            parameters = @Parameter(name = "date", in = ParameterIn.QUERY, description = "Date in format dd.MM.yyyy",
                    required = true, example = "10.01.2022"),
            responses = {
                    @ApiResponse(description = "Example response", responseCode = "200",
                            content = @Content(mediaType = "text/plain", examples = @ExampleObject("BTC"))),
                    @ApiResponse(description = "Not found response, returned if there's no values for a given" +
                            " date", responseCode = "404"),
                    @ApiResponse(description = "Bad request response, returned if the date is incorrect format or missing",
                            responseCode = "400", content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject("Cannot parse date. Please, pass date in dd.MM.yyyy format")))
            })
    public Mono<ServerResponse> getCryptocurrencyWithTheHighestNormalizedRangeForDate(ServerRequest serverRequest) {
        try {
            LocalDate localDate = extractDateParameter(serverRequest);
            return cryptocurrencyPriceService.getCryptocurrencyWithHighestNormalizedRange(localDate)
                    .flatMap(body -> RequestUtils.buildOkResponse(body, MediaType.TEXT_PLAIN))
                    .switchIfEmpty(RequestUtils.buildNotFoundResponse());
        } catch (IllegalStateException e) {
            return RequestUtils.buildBadRequestResponse(e.getMessage());
        } catch (DateTimeParseException e) {
            return RequestUtils.buildBadRequestResponse("Cannot parse date. Please, pass date in dd.MM.yyyy format");
        }
    }

    private LocalDate extractDateParameter(ServerRequest serverRequest)
            throws DateTimeParseException, IllegalStateException {
        String date = RequestUtils.extractRequiredQueryParam(serverRequest, "date");
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}
