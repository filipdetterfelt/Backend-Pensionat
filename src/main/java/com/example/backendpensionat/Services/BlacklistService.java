package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.BlacklistDetailedDTO;

public interface BlacklistService {
    BlacklistDetailedDTO checkBlackList(String email);
    BlacklistDetailedDTO checkBlackListAndSetOkToTrue(String email);
    BlacklistDetailedDTO checkBlackListAndSetOkToFalse(String email);
    void addToBlacklist(BlacklistDetailedDTO blacklistDetailedDTO);
    void updateBlacklistStatus(BlacklistDetailedDTO blacklistDTO);
}
