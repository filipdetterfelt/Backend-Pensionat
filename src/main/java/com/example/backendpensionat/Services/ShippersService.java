package com.example.backendpensionat.Services;


import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Models.Shippers;



public interface ShippersService {
    ShippersDetailedDTO shippersToDTO(Shippers Shippers);
    Shippers DTOtoShippers(ShippersDetailedDTO ShippersDetailedDTO);
    Shippers saveShipper(ShippersDetailedDTO dto);
}
