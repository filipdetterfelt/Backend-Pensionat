package com.example.backendpensionat.Repos;

import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<BookingDetailedDTO> findBookingByNumber(Long bookingId);
}
