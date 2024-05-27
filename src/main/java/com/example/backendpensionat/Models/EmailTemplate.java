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
    String Hälsningsfras;
    String RoomNumber;
    String RoomType;
    String CheckInDate;
    String CheckOutDate;
    String Price;
    String Farwell;
}
