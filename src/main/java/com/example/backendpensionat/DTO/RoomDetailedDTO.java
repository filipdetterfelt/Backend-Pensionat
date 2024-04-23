package com.example.backendpensionat.DTO;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Builder
public class RoomDetailedDTO {
    private Long id;
    private Long roomNumber = id;
    private Double price;
    private int size;
    List<BookingDTO> bookings;
}
