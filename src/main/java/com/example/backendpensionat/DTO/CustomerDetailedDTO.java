package com.example.backendpensionat.DTO;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailedDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String ssn;

        List<BookingDTO> bookings;
}
