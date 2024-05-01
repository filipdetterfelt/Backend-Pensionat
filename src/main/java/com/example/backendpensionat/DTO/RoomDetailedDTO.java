package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Enums.RoomType;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Builder
@Data
public class RoomDetailedDTO {
    private Long id;
    private Long roomNumber = id;
    private Double price;
    private RoomType roomType;
    List<BookingDTO> bookings;
}
