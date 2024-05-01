package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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


