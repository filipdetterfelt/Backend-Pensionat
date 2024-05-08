package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.DTO.ShippersTest;
import com.example.backendpensionat.Repos.ShippersRepo;
import com.example.backendpensionat.Services.ShippersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippersServiceIMPL implements ShippersService {

    @Autowired
    ShippersRepo shippersRepo;

    @Override
    public ShippersTest saveShipper(ShippersDetailedDTO dto){
        return shippersRepo.save(detailToCms(dto));

    }
}
