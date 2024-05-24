package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.EmailConfirmationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfirmationTemplateRepo extends JpaRepository<EmailConfirmationTemplate, Long>{
}
