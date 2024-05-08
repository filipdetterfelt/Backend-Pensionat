package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.BlacklistDetailedDTO;

public interface BlacklistService {
    BlacklistDetailedDTO checkBlackList(String email);
}
