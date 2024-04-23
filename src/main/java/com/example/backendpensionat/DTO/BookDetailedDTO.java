package com.example.backendpensionat.DTO;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Builder
public class BookDetailedDTO {
    private Long id;
    private int amount;
    private double totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    private RoomDTO roomDTO;
    private CustomerDTO customerDTO;


}


