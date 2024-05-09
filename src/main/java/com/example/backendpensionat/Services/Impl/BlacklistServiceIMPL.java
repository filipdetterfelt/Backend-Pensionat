package com.example.backendpensionat.Services.Impl;
import com.example.backendpensionat.DTO.BlacklistDetailedDTO;
import com.example.backendpensionat.Services.BlacklistService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class BlacklistServiceIMPL implements BlacklistService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public BlacklistDetailedDTO checkBlackList(String email) {
        String url = "https://javabl.systementor.se/api/koriander/blacklistcheck/" + email;
        return restTemplate.getForObject(url, BlacklistDetailedDTO.class);
    }

    @Override
    public BlacklistDetailedDTO checkBlackListAndSetOkToTrue(String email) {
        BlacklistDetailedDTO blacklistDetailedDTO = checkBlackList(email);
        blacklistDetailedDTO.setOk(true);
        return blacklistDetailedDTO;
    }
    @Override
    public BlacklistDetailedDTO checkBlackListAndSetOkToFalse(String email) {
        BlacklistDetailedDTO blacklistDetailedDTO = checkBlackList(email);
        blacklistDetailedDTO.setOk(false);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("https://javabl.systementor.se/api/koriander/blacklist", blacklistDetailedDTO);

        return blacklistDetailedDTO;
    }

    @Override
    public void addToBlacklist(BlacklistDetailedDTO blacklistDetailedDTO) {
        String blacklistURL = "https://javabl.systementor.se/api/koriander/blacklist";
        restTemplate.postForObject(blacklistURL, blacklistDetailedDTO, String.class);
    }

    @Override
    public void updateBlacklistStatus(BlacklistDetailedDTO blacklistDTO) {
        String url = "https://javabl.systementor.se/api/koriander/blacklist";
        restTemplate.put(url, blacklistDTO);
    }
}
