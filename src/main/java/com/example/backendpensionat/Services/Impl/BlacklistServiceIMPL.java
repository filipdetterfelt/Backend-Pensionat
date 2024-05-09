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

        restTemplate.put("https://javabl.systementor.se/api/koriander/blacklist", blacklistDetailedDTO);

        return blacklistDetailedDTO;
    }

    @Override
    public void addCustomerToBlacklist(String email) {
        String jsonBody = "{\"email\":\"" + email + "\", \"name\":\"null\", \"isOk\":\"false\" }";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/koriander/blacklist"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }

    @Override
    public void updateBlacklistStatus(String email) {
        BlacklistDetailedDTO blacklistDTO = checkBlackList(email);
        blacklistDTO.setOk(true);
        String url = "https://javabl.systementor.se/api/koriander/blacklist";
        restTemplate.put(url, blacklistDTO);
    }

    @Override
    public void updateCustomerInBlacklist(String email) {
        BlacklistDetailedDTO existingCustomer = checkBlackList(email);
        existingCustomer.setOk(true);

        String jsonBody = "{\"email\":\"" + email + "\", \"name\":\"" + existingCustomer.getName() + "\", \"isOk\":\"true\" }";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/koriander/blacklist"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }
}
