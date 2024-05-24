package com.example.backendpensionat.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class EmailConfirmationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String Hälsningsfras;
    private String RoomNumber;
    private String RoomType;
    private String CheckInDate;
    private String CheckOutDate;
    private String Price;
    private String Farwell;


    private static EmailConfirmationTemplate instance;


    public static EmailConfirmationTemplate getInstance() {
        if (instance == null) {
            instance = new EmailConfirmationTemplate();
        }
        return instance;
    }

}
