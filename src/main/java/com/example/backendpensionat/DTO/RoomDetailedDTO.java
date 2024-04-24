package com.example.backendpensionat.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Builder
@Data
public class RoomDetailedDTO {
    private Long id;
    private Long roomNumber = id;
    private Double price;
    private int size;
    List<BookingDTO> bookings;
}
