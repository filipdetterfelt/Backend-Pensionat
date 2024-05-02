package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Customer;
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
    private CustomerDetailedDTO customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer amountOfBeds;
    private RoomType roomType;
}
