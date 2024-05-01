package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.Models.Booking;

import java.util.List;

public interface BookingService {
    BookingDTO bookingToDTO(Booking booking);
    BookingDetailedDTO bDetailedToDTO(Booking booking);
    Booking detailToBooking(BookingDetailedDTO bookingDetailedDTO);
    List<BookingDetailedDTO> listAllBookings();
    void deleteBookingById(Long id);
    BookingDetailedDTO findBookingById(Long id);
    void updateBooking(BookingDetailedDTO bookingDTO);
    void saveBooking(BookingDetailedDTO booking);
}
