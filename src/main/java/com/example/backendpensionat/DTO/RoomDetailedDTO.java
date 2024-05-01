package com.example.backendpensionat.DTO;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailedDTO {
    private Long id;
    private Long roomNumber = id;
    private Double price;
    private int maxBeds;
    private int size;
    List<BookingDTO> bookings;
}
