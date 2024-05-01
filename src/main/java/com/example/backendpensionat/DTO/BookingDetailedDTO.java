package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Enums.RoomType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class BookingDetailedDTO {
    private Long id;
    private int amountOfBeds;
    private Double totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private String roomNumber = "";

    private RoomDetailedDTO room;
    private CustomerDTO customerDTO;
}


