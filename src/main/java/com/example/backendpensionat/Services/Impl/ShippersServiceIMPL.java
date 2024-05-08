package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Models.Shippers;
import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Repos.ShippersRepo;
import com.example.backendpensionat.Services.ShippersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippersServiceIMPL implements ShippersService {

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


    @Autowired
    ShippersRepo shippersRepo;

    @Override
    public Shippers saveShipper(ShippersDetailedDTO dto){
        return shippersRepo.save(DTOtoShippers(dto));

    }
}
