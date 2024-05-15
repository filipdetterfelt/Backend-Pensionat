package com.example.backendpensionat;

import com.example.backendpensionat.Services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@RequiredArgsConstructor
public class SyncContractCustomers implements CommandLineRunner {

    private final ContractCustomerService contractCustomerService;

    @Override
    public void run(String... args) throws Exception {
        contractCustomerService.getAndSaveContractCustomers(false);
    }
}
