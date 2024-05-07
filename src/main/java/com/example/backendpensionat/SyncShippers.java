package com.example.backendpensionat;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.DTO.ShippersList;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.CommandLineRunner;

import java.net.URL;

public class SyncShippers implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        ShippersList shippersList = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/shippers"), ShippersList.class);

        for (ShippersDetailedDTO shipper : shippersList.shippersDTOList){
            System.out.println(shipper.getCompanyName());
            System.out.println(shipper.getPhone());
        }
    }
}
