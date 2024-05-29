package com.example.backendpensionat;

import com.example.backendpensionat.PropertiesConfigs.IntegrationPropertiesConfig;
import com.example.backendpensionat.Services.ShippersService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@RequiredArgsConstructor
public class SyncShippers implements CommandLineRunner {
    private final ShippersService shippersService;
    private final IntegrationPropertiesConfig integrationPropertiesConfig;
    @Override
    public void run(String... args) throws Exception {
        shippersService.getAndSaveShippers(integrationPropertiesConfig.getShippersUrl());
    }
}
