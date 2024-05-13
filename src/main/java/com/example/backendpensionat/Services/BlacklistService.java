package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.BlacklistDetailedDTO;

public interface BlacklistService {
    BlacklistDetailedDTO checkBlackList(String email);
    BlacklistDetailedDTO checkBlackListAndSetOkToTrue(String email);
    BlacklistDetailedDTO checkBlackListAndSetOkToFalse(String email);
    void addCustomerToBlacklist(String email, String name);
    void updateCustomerInBlacklistToTrue(String email);
    void updateCustomerInBlacklistToFalse(String email);
}
