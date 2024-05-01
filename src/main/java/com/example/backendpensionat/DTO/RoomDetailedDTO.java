package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Enums.RoomType;
import lombok.Builder;
import lombok.Data;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailedDTO {
    private Long id;
    private Long roomNumber;
    private Double price;
    private RoomType roomType;
    List<BookingDTO> bookings;
}
