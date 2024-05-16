package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.*;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.BookingService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
                .customerDTO(CustomerDTO.builder().id(booking.getCustomer().getId()).build())
                .roomNumber(String.valueOf(booking.getRoom().getRoomNumber())).build();
    }

    @Override
    public Booking detailToBooking(BookingDetailedDTO bookDTO) {
        Double totalPrice = calculateTotalPrice(bookDTO.getStartDate(), bookDTO.getEndDate(), bookDTO.getRoom().getPrice());

        return Booking.builder()
                .id(bookDTO.getId())
                .amountOfBeds(bookDTO.getAmountOfBeds())
                .totalPrice(totalPrice)
                .startDate(bookDTO.getStartDate())
                .endDate(bookDTO.getEndDate())
                .customer(customerRepo.findById(bookDTO.getCustomerDTO().getId()).orElse(null))
                .room(roomRepo.findById(bookDTO.getRoom().getId()).orElse(null))
                .build();
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

    @Override
    public Double calculateTotalPrice(LocalDate startDate, LocalDate endDate, Double roomPrice) {
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        List<Double> taxedDays = new ArrayList<>();
        System.out.println(roomPrice);

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            System.out.println("inside for loop");
            Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Date d = Date.from(instant);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            System.out.println("Day of week: " + dayOfWeek);

            if(dayOfWeek == 1) {
                taxedDays.add(roomPrice - (roomPrice * 0.02));
            } else {
                taxedDays.add(roomPrice);
            }
        }
        System.out.println("list size: " + taxedDays.size());
        double totalPrice = taxedDays.stream().reduce(Double::sum).get();
        System.out.println("after price:" + totalPrice);

        if(numberOfDays >= 2) {
            System.out.println("days is more than 2");
            return totalPrice - (totalPrice * 0.005);
        } else {
            return totalPrice;
        }
    }




}
