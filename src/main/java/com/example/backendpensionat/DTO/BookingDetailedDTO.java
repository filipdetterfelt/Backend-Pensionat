package com.example.backendpensionat.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class BookingDetailedDTO {
    private Long id;
    private int amountOfBeds;
    private double totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    private RoomDTO roomDTO;
    private CustomerDTO customerDTO;




}


