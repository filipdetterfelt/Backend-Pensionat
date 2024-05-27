package com.example.backendpensionat.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailTemplate {
    @Id
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
