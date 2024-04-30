package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSearchDTO {
    private Long customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private RoomType roomType;
}
