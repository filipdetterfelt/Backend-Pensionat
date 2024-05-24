package com.example.backendpensionat.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmailConfirmationTemplateDTO {
    String Hälsningsfras;
    String RoomNumber;
    String RoomType;
    String CheckInDate;
    String CheckOutDate;
    String Price;
    String Farwell;
}
