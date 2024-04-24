package com.example.backendpensionat.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
public class BookingDetailedDTO {
    private Long id;
    private int amount;
    private double totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    private RoomDTO roomDTO;
    private CustomerDTO customerDTO;




}


