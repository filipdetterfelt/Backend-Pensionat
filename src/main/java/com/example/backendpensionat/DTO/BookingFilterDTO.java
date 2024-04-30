package com.example.backendpensionat.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingFilterDTO {
    private String startDate;
    private String endDate;
    private String customerName;
}
