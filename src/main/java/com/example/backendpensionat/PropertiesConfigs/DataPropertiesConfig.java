package com.example.backendpensionat.PropertiesConfigs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "userdata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataPropertiesConfig {
    String fromEmail;
    String resetPasswordUrl;
}
