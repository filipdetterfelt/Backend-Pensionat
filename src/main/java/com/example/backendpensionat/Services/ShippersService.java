package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.ShippersDTO;
import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Shippers;

public interface ShippersService {
    ShippersDTO shippersToDTO(Shippers shippers);

}
