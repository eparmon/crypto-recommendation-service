package com.xm.recommendation.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("cryptocurrency_prices")
public class CryptocurrencyPrice {

    @Id
    private Long id;

    private String cryptocurrency;

    private Long timestamp;

    private BigDecimal valueUsd;

}
