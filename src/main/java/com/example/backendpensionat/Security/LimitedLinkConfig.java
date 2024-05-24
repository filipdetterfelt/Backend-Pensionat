package com.example.backendpensionat.Security;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LimitedLinkConfig {
    private Map<String, LocalDateTime> linkStorage = new HashMap<String, LocalDateTime>();

    public String generateLink(int validHours) {
        String linkId = UUID.randomUUID().toString();
        LocalDateTime expiryTime = LocalDateTime.now().plusHours(validHours);
        linkStorage.put(linkId, expiryTime);
        return linkId;
    }

    public boolean isLinkValid(String linkId) {
        LocalDateTime expiryTime = linkStorage.get(linkId);
        if(expiryTime == null) {
            return false;
        }
        return LocalDateTime.now().isBefore(expiryTime);
    }

    public String getLink(String linkId){
        if(isLinkValid(linkId)) {
            return "http://localhost:8080/changePassword/" + linkId;
        }
        return null;
    }
}
