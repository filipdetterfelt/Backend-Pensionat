package com.example.backendpensionat;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.CommandLineRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.net.URL;


import java.util.List;

@Component
public class SyncShippers implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());


            //Byt till JsonMapper?
            List<ShippersDetailedDTO> shippersList = objectMapper.readValue(new URL("https://javaintegration.systementor.se/shippers")
                    , objectMapper.getTypeFactory().constructCollectionType(List.class, ShippersDetailedDTO.class));

            for (ShippersDetailedDTO shipper : shippersList){
                System.out.println("Company name: " + shipper.companyName);
                System.out.println("Contact name: " + shipper.contactName);
            }

        //contractCustomerList.contractCustomerDTOList.forEach(contractCustomerService::saveContractCustomer);

    }
}
