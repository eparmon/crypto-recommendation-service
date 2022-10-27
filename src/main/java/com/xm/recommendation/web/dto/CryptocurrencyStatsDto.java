package com.xm.recommendation.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptocurrencyStatsDto {

    private String name;
    private BigDecimal oldestPrice;
    private BigDecimal newestPrice;
    private BigDecimal lowestPrice;
    private BigDecimal highestPrice;

}
