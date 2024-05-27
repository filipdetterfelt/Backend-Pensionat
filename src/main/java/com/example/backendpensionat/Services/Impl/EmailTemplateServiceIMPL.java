package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.EmailTemplateDTO;
import com.example.backendpensionat.Models.EmailTemplate;
import com.example.backendpensionat.Repos.EmailTemplateRepo;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateServiceIMPL {
    public EmailTemplate DTOtoEmailTemplate(EmailTemplateDTO EmailTemplateDTO){
        return EmailTemplate.builder()
                .id(EmailTemplateDTO.getId())
                .subject(EmailTemplateDTO.getSubject())
                .greetingPhrase1(EmailTemplateDTO.getGreetingPhrase1())
                .greetingPhrase2(EmailTemplateDTO.getGreetingPhrase2())
                .roomNumber(EmailTemplateDTO.getRoomNumber())
                .roomType(EmailTemplateDTO.getRoomType())
                .checkInDate(EmailTemplateDTO.getCheckInDate())
                .checkOutDate(EmailTemplateDTO.getCheckOutDate())
                .price(EmailTemplateDTO.getPrice())
                .farewell(EmailTemplateDTO.getFarewell())
                .build();
    }

    public EmailTemplateDTO emailTemplateToDTO(EmailTemplate emailTemplate){
        return EmailTemplateDTO.builder()
                .id(emailTemplate.getId())
                .subject(emailTemplate.getSubject())
                .greetingPhrase1(emailTemplate.getGreetingPhrase1())
                .greetingPhrase2(emailTemplate.getGreetingPhrase2())
                .roomNumber(emailTemplate.getRoomNumber())
                .roomType(emailTemplate.getRoomType())
                .checkInDate(emailTemplate.getCheckInDate())
                .checkOutDate(emailTemplate.getCheckOutDate())
                .price(emailTemplate.getPrice())
                .farewell(emailTemplate.getFarewell())
                .build();
    }

}
