package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Models.Shippers;
import com.example.backendpensionat.PropertiesConfigs.IntegrationPropertiesConfig;
import com.example.backendpensionat.Repos.ShippersRepo;
import com.example.backendpensionat.Services.ShippersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class ShippersServiceIMPL implements ShippersService {

    ShippersRepo shippersRepo;

    @Autowired
    IntegrationPropertiesConfig integrationPropertiesConfig;

    public ShippersServiceIMPL(ShippersRepo shippersRepo) {
        this.shippersRepo = shippersRepo;
    }

    @Override
    public List<ShippersDetailedDTO> getShippersFromJSON(URL url) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(url
                , objectMapper.getTypeFactory().constructCollectionType(List.class, ShippersDetailedDTO.class));
    }

    @Override
    public void getAndSaveShippers(String path) throws IOException {
        URL url = new URL(path);

        for(ShippersDetailedDTO s: getShippersFromJSON(url)) {
            Optional<Shippers> shipper = shippersRepo.findShippersByExternalId(s.externalId);
            if(shipper.isEmpty()) {
                shipper = Optional.of(new Shippers());
            }
            shipper.get().setExternalId(s.externalId);
            shipper.get().setCompanyName(s.companyName);
            shipper.get().setPhone(s.phone);

            shippersRepo.save(shipper.get());
         }
    }

    @Override
    public ShippersDetailedDTO shippersToDTO(Shippers Shippers) {
        return ShippersDetailedDTO.builder()
                .internalId(Shippers.getId())
                .externalId(Shippers.getExternalId())
                .companyName(Shippers.getCompanyName())
                .phone(Shippers.getPhone()).build();
    }

    @Override
    public Shippers DTOtoShippers(ShippersDetailedDTO ShippersDetailedDTO) {
        List<Shippers> allShippers = shippersRepo.findAll();

        Shippers matchingShippers = allShippers.stream().filter(shippers ->
                shippers.getExternalId().equals(ShippersDetailedDTO.externalId)).findFirst().orElse(new Shippers());

        return Shippers.builder()
                .id(matchingShippers.getId())
                .externalId(ShippersDetailedDTO.getExternalId())
                .companyName(ShippersDetailedDTO.getCompanyName())
                .phone(ShippersDetailedDTO.getPhone()).build();
    }




    @Override
    public Shippers saveShipper(ShippersDetailedDTO dto){
        return shippersRepo.save(DTOtoShippers(dto));

    }
}
