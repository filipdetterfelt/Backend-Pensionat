package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.Repos.TokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepo tokenRepo;

    @Transactional
    public void deleteByToken(String token) {
        tokenRepo.deleteByToken(token);
    }
}
