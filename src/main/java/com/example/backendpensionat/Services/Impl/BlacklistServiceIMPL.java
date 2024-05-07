package com.example.backendpensionat.Services.Impl;
import com.example.backendpensionat.Repos.BlacklistRepo;
import com.example.backendpensionat.Services.BlacklistService;
import org.springframework.stereotype.Service;


@Service
public class BlacklistServiceIMPL implements BlacklistService {

    private final BlacklistRepo blacklistRepo;

    public BlacklistServiceIMPL(BlacklistRepo blacklistRepo) {
        this.blacklistRepo = blacklistRepo;
    }
}
