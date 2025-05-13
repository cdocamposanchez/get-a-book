package com.adi.gab.infrastructure.config.stripe;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "stripe")
@Getter
@Setter
public class StripeProperties {
    private String secretKey;
    private String successUrl;
    private String cancelUrl;
}