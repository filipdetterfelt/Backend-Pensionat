package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.RoomDTO;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.BookingService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class BookingServiceIMPL implements BookingService {

    private final CustomerRepo customerRepo;
    private final BookingRepo bookingRepo;
    private final RoomRepo roomRepo;

    @Override
    public BookingDTO bookingToDTO(Booking booking) {

        return BookingDTO.builder().id(booking.getId()).build();
    }

    @Override
    public BookingDetailedDTO bDetailedToDTO(Booking booking) {

        return BookingDetailedDTO.builder().id(booking.getId())
                .amountOfBeds(booking.getAmountOfBeds())
                .totalPrice(booking.getTotalPrice())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .roomDTO(RoomDTO.builder().id(booking.getRoom().getId()).build())
                .customerDTO(CustomerDTO.builder().id(booking.getCustomer().getId()).build()).build();
    }

    @Override
    public Booking detailToBooking(BookingDetailedDTO bookDTO) {

        return Booking.builder().Id(bookDTO.getId())
                .amountOfBeds(bookDTO.getAmountOfBeds())
                .totalPrice(bookDTO.getTotalPrice())
                .startDate(bookDTO.getStartDate())
                .endDate(bookDTO.getEndDate())
                .customer(customerRepo.findById(bookDTO.getCustomerDTO().getId()).orElse(null))
                .room(roomRepo.findById(bookDTO.getRoomDTO().getId()).orElse(null)).build();
    }

    @Override
    public List<BookingDetailedDTO> listAllBookings() {
        return bookingRepo.findAll().stream().map(this::bDetailedToDTO).toList();
    }

    @Override
    public void deleteBookingById(Long id) {
        bookingRepo.deleteById(id);
    }

    @Override
    public BookingDetailedDTO findBookingById(Long id) {
        return bDetailedToDTO(bookingRepo.findById(id).orElse(null));
    }

    @Override
    public void updateBooking(BookingDetailedDTO bookingDTO) {
        Booking existingBooking = bookingRepo.findById(bookingDTO.getId()).orElse(null);
        existingBooking.setStartDate(bookingDTO.getStartDate());
        existingBooking.setEndDate(bookingDTO.getEndDate());
        existingBooking.setAmountOfBeds(bookingDTO.getAmountOfBeds());
        bookingRepo.save(existingBooking);
    }
}
