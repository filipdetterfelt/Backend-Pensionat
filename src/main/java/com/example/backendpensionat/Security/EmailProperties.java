package com.example.backendpensionat.Security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailProperties {
    private String confirmationRecipient;
    private String forgotPasswordRecipient;
}