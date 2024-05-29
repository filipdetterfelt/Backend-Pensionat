package com.example.backendpensionat.Services.Impl;
import com.example.backendpensionat.DTO.BlacklistDTO;
import com.example.backendpensionat.DTO.BlacklistDetailedDTO;
import com.example.backendpensionat.PropertiesConfigs.BlacklistPropertiesConfig;
import com.example.backendpensionat.Services.BlacklistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@Service
public class BlacklistServiceIMPL implements BlacklistService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    BlacklistPropertiesConfig blacklistPropertiesConfig;

    @Override
    public BlacklistDetailedDTO checkBlackList(String email) {
        String url = blacklistPropertiesConfig.getCheckBlacklistUrl() + "/"+  email;
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
    public List<BlacklistDTO> getBlacklist(boolean isTest) throws IOException {
        URL url;
        if (isTest) {
            url = getClass().getClassLoader().getResource("./XmlJsonFiles/blacklist.json");
        } else {
            url = new URL(blacklistPropertiesConfig.getBlacklistUrl());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(url
                , objectMapper.getTypeFactory().constructCollectionType(List.class, BlacklistDTO.class));
    }

    @Override
    public void addCustomerToBlacklist(String email, String name) {
        String jsonBody = "{\"email\":\"" + email + "\", \"name\":\"" + name + "\", \"isOk\":\"false\" }";

        BlacklistDetailedDTO newBlacklistedCustomer = new BlacklistDetailedDTO();
        newBlacklistedCustomer.setEmail(email);
        newBlacklistedCustomer.setOk(false);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(blacklistPropertiesConfig.getBlacklistUrl()))
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
    public void updateCustomerInBlacklistToTrue(String email) {
        BlacklistDetailedDTO existingCustomer = checkBlackListAndSetOkToTrue(email);
        jsonBodyExisting(email, existingCustomer);
    }

    @Override
    public void updateCustomerInBlacklistToFalse(String email) {
        BlacklistDetailedDTO existingCustomer = checkBlackListAndSetOkToFalse(email);
        jsonBodyExisting(email, existingCustomer);
    }

    private void jsonBodyExisting(String email, BlacklistDetailedDTO existingCustomer) {
        String jsonBody = "{\"name\": \"" + existingCustomer.getName() + "\", \"isOk\":\"" + existingCustomer.isOk() + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(blacklistPropertiesConfig.getBlacklistUrl() + "/"+ email))
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
