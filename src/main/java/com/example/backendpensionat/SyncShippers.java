package com.example.backendpensionat;

import com.example.backendpensionat.DTO.ShippersList;
import com.example.backendpensionat.DTO.ShippersTest;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.CommandLineRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import com.example.backendpensionat.DTO.ShippersList;



import java.net.URL;

@Component
public class SyncShippers implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        URL url = new URL("https://javaintegration.systementor.se/shippers");
        ShippersList shippersList = objectMapper.readValue(url,ShippersList.class);

        for (ShippersTest shipper : shippersList.shippersTestList) {
            System.out.println(shipper.city);
        }

    }
}
