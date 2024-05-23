package com.example.backendpensionat.Security;

import com.example.backendpensionat.Repos.PasswordResetRepo;
import com.example.backendpensionat.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordResetRepo passwordResetRepo;
}
