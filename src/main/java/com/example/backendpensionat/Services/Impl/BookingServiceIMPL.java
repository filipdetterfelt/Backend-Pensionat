package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.*;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.BookingService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Data
public class BookingServiceIMPL implements BookingService {

    private final CustomerRepo customerRepo;
    private final BookingRepo bookingRepo;
    private final RoomRepo roomRepo;

    public BookingServiceIMPL(CustomerRepo customerRepo, BookingRepo bookingRepo, RoomRepo roomRepo) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
    }

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
                .room(RoomDetailedDTO.builder()
                        .id(booking.getRoom().getId())
                        .roomNumber(booking.getRoom().getRoomNumber())
                        .price(booking.getRoom().getPrice())
                        .roomType(booking.getRoom().getRoomType())
                        .build())
                .customerDTO(CustomerDTO.builder().id(booking.getCustomer().getId()).build()).build();
    }

    @Override
    public Booking detailToBooking(BookingDetailedDTO bookDTO) {

        return Booking.builder().Id(bookDTO.getId())
                .amountOfBeds(bookDTO.getAmountOfBeds())
                .startDate(bookDTO.getStartDate())
                .endDate(bookDTO.getEndDate())
                .customer(customerRepo.findById(bookDTO.getCustomerDTO().getId()).orElse(null))
                .room(roomRepo.findById(bookDTO.getRoom().getId()).orElse(null)).build();
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
        return bDetailedToDTO(Objects.requireNonNull(bookingRepo.findById(id).orElse(null)));
    }

    @Override
    public void updateBooking(BookingDetailedDTO bookingDTO) {
        bookingRepo.save(detailToBooking(bookingDTO));
    }

    @Override
    public void saveBooking(BookingDetailedDTO booking) {
        bookingRepo.save(detailToBooking(booking));
    }
}
