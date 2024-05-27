package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.DTO.EmailTemplateDTO;
import com.example.backendpensionat.Models.EmailTemplate;
import com.example.backendpensionat.Repos.EmailTemplateRepo;
import org.hibernate.validator.constraints.Email;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateServiceIMPL {
    private final EmailTemplateRepo emailTemplateRepo;

    public EmailTemplateServiceIMPL(EmailTemplateRepo emailTemplateRepo) {
        this.emailTemplateRepo = emailTemplateRepo;
    }

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

    public EmailTemplateDTO getTemplate() {
        EmailTemplate template = emailTemplateRepo.findById(1L).orElse(null);
        if(template != null) {
            return emailTemplateToDTO(template);
        } else {
            return new EmailTemplateDTO();
        }
    }

    public String emailMessage(EmailTemplateDTO template, CustomerDetailedDTO customer, BookingDetailedDTO booking) {
        return String.format("""
                %s %s %s,
                
                %s
                
                Booking details:
                %s: %d
                %s: %s
                %s: %s
                %s: %s
                
                %s: %s SEK
               
                %s
                """,
                template.getGreetingPhrase1(), customer.getFirstName(), customer.getLastName(),
                template.getGreetingPhrase2(),
                template.getRoomNumber(), booking.getRoom().getRoomNumber(),
                template.getRoomType(), booking.getRoom().getRoomType(),
                template.getCheckInDate(), booking.getStartDate(),
                template.getCheckOutDate(), booking.getEndDate(),
                template.getPrice(), booking.getTotalPrice(),
                template.getFarewell());
    }

}
