package com.example.backendpensionat.PropertiesConfigs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "integration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationPropertiesConfig {
    String baseUrl;
    String checkBlacklistUrl;
    String blacklistUrl;
    String contractCustomersUrl;
}
