package com.example.backendpensionat.Services;


import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Models.Shippers;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public interface ShippersService {
    List<ShippersDetailedDTO> getShippersFromJSON(URL url) throws IOException;
    void getAndSaveShippers(String url) throws IOException;

    ShippersDetailedDTO shippersToDTO(Shippers Shippers);
    Shippers DTOtoShippers(ShippersDetailedDTO ShippersDetailedDTO);
}
