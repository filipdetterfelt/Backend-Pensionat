package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Models.Shippers;
import com.example.backendpensionat.Services.ShippersService;
import org.springframework.stereotype.Service;

@Service
public class ShippersServiceIMPL implements ShippersService {

    @Override
    public ShippersDetailedDTO shippersToDTO(Shippers Shippers) {
        return ShippersDetailedDTO.builder()
                .externalId(Shippers.getExternalId())
                .companyName(Shippers.getCompanyName())
                .phone(Shippers.getPhone()).build();
    }

    @Override
    public Shippers DTOtoShippers(ShippersDetailedDTO ShippersDetailedDTO) {
        return Shippers.builder()
                .externalId(ShippersDetailedDTO.getExternalId())
                .companyName(ShippersDetailedDTO.getCompanyName())
                .phone(ShippersDetailedDTO.getPhone()).build();
    }

}
