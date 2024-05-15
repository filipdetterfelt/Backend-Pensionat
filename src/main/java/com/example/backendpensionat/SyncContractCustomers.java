package com.example.backendpensionat;

import com.example.backendpensionat.DTO.ContractCustomerList;
import com.example.backendpensionat.Services.ContractCustomerService;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.net.URL;
@Component
@ComponentScan
@RequiredArgsConstructor
public class SyncContractCustomers implements CommandLineRunner {

    private final ContractCustomerService contractCustomerService;

    @Override
    public void run(String... args) throws Exception {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        ContractCustomerList contractCustomerList = xmlMapper.readValue(
                new URL("https://javaintegration.systementor.se/customers"), ContractCustomerList.class);

        contractCustomerList.contractCustomerDTOList.forEach(contractCustomerService::saveContractCustomer);
    }
}
