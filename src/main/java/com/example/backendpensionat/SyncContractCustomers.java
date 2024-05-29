package com.example.backendpensionat;

import com.example.backendpensionat.PropertiesConfigs.IntegrationPropertiesConfig;
import com.example.backendpensionat.Services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
@RequiredArgsConstructor
public class SyncContractCustomers implements CommandLineRunner {

    private final ContractCustomerService contractCustomerService;
    private final IntegrationPropertiesConfig integrationPropertiesConfig;

    @Override
    public void run(String... args) throws Exception {
        contractCustomerService.getAndSaveContractCustomers(new URL(integrationPropertiesConfig.getContractCustomersUrl()));
    }
}
