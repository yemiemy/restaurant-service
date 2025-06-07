package com.restaurant.picker.restaurantservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.api")
@Data
public class GoogleApiConfig {
    private String key;
}
