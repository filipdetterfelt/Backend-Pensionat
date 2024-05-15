package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.BlacklistDTO;
import com.example.backendpensionat.DTO.BlacklistDetailedDTO;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface BlacklistService {
    BlacklistDetailedDTO checkBlackList(String email);
    BlacklistDetailedDTO checkBlackListAndSetOkToTrue(String email);
    BlacklistDetailedDTO checkBlackListAndSetOkToFalse(String email);
    List<BlacklistDTO> getBlacklist(boolean isTest) throws IOException;
    void addCustomerToBlacklist(String email, String name);
    void updateCustomerInBlacklistToTrue(String email);
    void updateCustomerInBlacklistToFalse(String email);
}
