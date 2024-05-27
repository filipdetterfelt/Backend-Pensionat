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
                .Hälsningsfras(EmailTemplateDTO.getHälsningsfras())
                .RoomNumber(EmailTemplateDTO.getRoomNumber())
                .RoomType(EmailTemplateDTO.getRoomType())
                .CheckInDate(EmailTemplateDTO.getCheckInDate())
                .CheckOutDate(EmailTemplateDTO.getCheckOutDate())
                .Price(EmailTemplateDTO.getPrice())
                .Farwell(EmailTemplateDTO.getFarwell())
                .build();

    }

    public EmailTemplateDTO emailTemplateToDTO(EmailTemplate emailTemplate){
        return EmailTemplateDTO.builder()
                .id(emailTemplate.getId())
                .Hälsningsfras(emailTemplate.getHälsningsfras())
                .RoomNumber(emailTemplate.getRoomNumber())
                .RoomType(emailTemplate.getRoomType())
                .CheckInDate(emailTemplate.getCheckInDate())
                .CheckOutDate(emailTemplate.getCheckOutDate())
                .Price(emailTemplate.getPrice())
                .Farwell(emailTemplate.getFarwell())
                .build();

    }

}
