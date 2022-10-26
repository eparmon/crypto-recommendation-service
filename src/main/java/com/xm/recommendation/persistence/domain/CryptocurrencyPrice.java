package com.xm.recommendation.persistence.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table("cryptocurrency_prices")
public class CryptocurrencyPrice {

    @Id
    private Long id;

    private String cryptocurrency;

    private Long timestamp;

    private BigDecimal valueUsd;

}
