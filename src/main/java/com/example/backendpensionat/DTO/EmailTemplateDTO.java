package com.example.backendpensionat.DTO;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmailTemplateDTO {
    private Long id;
    private String subject;
    private String greetingPhrase1;
    private String greetingPhrase2;
    private String roomNumber;
    private String roomType;
    private String checkInDate;
    private String checkOutDate;
    private String price;
    private String farewell;
}
