package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.ContractCustomerDTO;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Models.Shippers;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.DTO.ShippersTest;

public interface ShippersService {
    ShippersDetailedDTO shippersToDTO(Shippers Shippers);
    Shippers DTOtoShippers(ShippersDetailedDTO ShippersDetailedDTO);
    ShippersTest saveShipper(ShippersDetailedDTO dto);
}
