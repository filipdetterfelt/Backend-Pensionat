package com.example.backendpensionat.DTO;

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
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxBeds;
}
