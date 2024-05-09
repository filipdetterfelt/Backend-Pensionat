package com.example.backendpensionat.Services.Impl;
import com.example.backendpensionat.DTO.BlacklistDetailedDTO;
import com.example.backendpensionat.Services.BlacklistService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class BlacklistServiceIMPL implements BlacklistService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public BlacklistDetailedDTO checkBlackList(String email) {
        String url = "https://javabl.systementor.se/api/Stefan/blacklistcheck/" + email;
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
        return blacklistDetailedDTO;
    }
    @Override
    public void sendBlacklistData(String email, String name, boolean isOk) {
        BlacklistDetailedDTO blacklistDetailedDTO = checkBlackListAndSetOkToFalse(email);
        System.out.println(blacklistDetailedDTO.ok + " " + blacklistDetailedDTO.statusText);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/stefan/blacklist"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":\"" + email + "\", \"name\":\"" + name + "\", \"isOk\":" + isOk + " }"))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }
}
