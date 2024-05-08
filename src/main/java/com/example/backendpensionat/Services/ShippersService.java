package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.DTO.ShippersTest;

public interface ShippersService {
    ShippersTest saveShipper(ShippersDetailedDTO dto);
}
