package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {

}
