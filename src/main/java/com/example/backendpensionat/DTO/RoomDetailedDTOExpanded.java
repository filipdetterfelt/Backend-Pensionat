package com.example.backendpensionat.DTO;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class RoomDetailedDTOExpanded {
    private Long id;
    private Long roomNumber;
    private Double price;
    private int maxBeds;
    private int size;
    List<BookingDetailedDTO> bookings;
}