package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.Models.Booking;

public interface BookingService {
    BookingDTO bookingToDTO(Booking booking);
    BookingDetailedDTO bDetailedToDTO(Booking booking);
    Booking detailToBooking(BookingDetailedDTO bookingDetailedDTO);
}
