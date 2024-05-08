package com.example.backendpensionat;

import com.example.backendpensionat.DTO.ShippersList;
import com.example.backendpensionat.DTO.ShippersTest;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.CommandLineRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.net.URL;



import java.net.URL;
import java.util.List;

@Component
public class SyncShippers implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());


            //Byt till JsonMapper?
            List<ShippersTest> shippersList = objectMapper.readValue(new URL("https://javaintegration.systementor.se/shippers")
                    , objectMapper.getTypeFactory().constructCollectionType(List.class,ShippersTest.class));

            for (ShippersTest shipper : shippersList){
                System.out.println("Company name: " + shipper.companyName);
                System.out.println("Contact name: " + shipper.contactName);
            }

        //contractCustomerList.contractCustomerDTOList.forEach(contractCustomerService::saveContractCustomer);

    }
}
