package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    void deleteByToken(String token);
}
