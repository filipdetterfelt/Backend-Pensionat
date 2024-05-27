package com.example.backendpensionat.DTO;

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
    private String Hälsningsfras;
    private String RoomNumber;
    private String RoomType;
    private String CheckInDate;
    private String CheckOutDate;
    private String Price;
    private String Farwell;
}
