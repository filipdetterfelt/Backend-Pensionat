package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepo extends JpaRepository<EmailTemplate, Long>{
}
