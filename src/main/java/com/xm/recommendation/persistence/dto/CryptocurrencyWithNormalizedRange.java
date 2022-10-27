package com.xm.recommendation.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptocurrencyWithNormalizedRange {

    private String cryptocurrency;
    private BigDecimal normalizedRange;

}
