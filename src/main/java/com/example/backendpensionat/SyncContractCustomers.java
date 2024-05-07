package com.example.backendpensionat;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.DTO.ContractCustomerList;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class SyncContractCustomers implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        ContractCustomerList contractCustomerList = xmlMapper.readValue(
                new URL("https://javaintegration.systementor.se/customers"), ContractCustomerList.class);

        for( ContractCustomerDTO customer : contractCustomerList.contractCustomerDTOList ){
            System.out.println(customer.contactName);
            System.out.println(customer.country);
        }
    }
}
