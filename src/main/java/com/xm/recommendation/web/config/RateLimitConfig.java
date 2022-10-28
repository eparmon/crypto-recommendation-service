package com.xm.recommendation.web.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Value("${app.rate-limit.requests-allowed-per-period}")
    private int requestsAllowedPerPeriod;

    @Value("${app.rate-limit.period-millis}")
    private int periodInMillis;

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(requestsAllowedPerPeriod)
                .limitRefreshPeriod(Duration.ofMillis(periodInMillis))
                .build();
        return RateLimiterRegistry.of(config);
    }

}
