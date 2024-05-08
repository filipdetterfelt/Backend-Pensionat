package com.example.backendpensionat;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Services.Impl.ShippersServiceIMPL;
import com.example.backendpensionat.Services.ShippersService;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import java.net.URL;


import java.util.List;

@ComponentScan
@RequiredArgsConstructor
public class SyncShippers implements CommandLineRunner {
    private final ShippersService shippersService;
    @Override
    public void run(String... args) throws Exception {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());


            //Byt till JsonMapper?
            List<ShippersDetailedDTO> shippersList = objectMapper.readValue(new URL("https://javaintegration.systementor.se/shippers")
                    , objectMapper.getTypeFactory().constructCollectionType(List.class, ShippersDetailedDTO.class));



        shippersList.forEach(shippersService::saveShipper);


    }
}
