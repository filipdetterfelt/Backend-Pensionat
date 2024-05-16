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
        return Booking.builder()
                .id(bookDTO.getId())
                .amountOfBeds(bookDTO.getAmountOfBeds())
                .totalPrice(bookDTO.getTotalPrice())
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
    public Double calculateTotalPrice(LocalDate startDate, LocalDate endDate, Double roomPrice, CustomerDetailedDTO customer) {
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        List<Double> dayPrices = new ArrayList<>();

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if(dateIsSunday(date)) {
                dayPrices.add(twoPercentOff(roomPrice));
            } else {
                dayPrices.add(roomPrice);
            }
        }
        double totalPrice = dayPrices.stream().reduce(Double::sum).orElse(0.0);

        totalPrice =  numberOfDays >= 2? halfPercentOff(totalPrice): totalPrice;
        totalPrice = hasBookedTenDaysThisYear(customer.getBookings())? twoPercentOff(totalPrice): totalPrice;

        return totalPrice;
    }

    public boolean dateIsSunday(LocalDate date) {
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date d = Date.from(instant);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == 1;
    }

    public Double twoPercentOff(Double price) {
        return price * 0.98;
    }

    public Double halfPercentOff(Double price) {
        return price * 0.995;
    }

    public boolean hasBookedTenDaysThisYear(List<BookingDTO> customerBookings) {
        List<Booking> bookings = customerBookings.stream().map(booking -> bookingRepo.findById(booking.getId()).orElse(null)).toList();
        long count = 0;
        for (Booking b: bookings) {
            for (LocalDate date = b.getStartDate(); date.isBefore(b.getEndDate()); date = date.plusDays(1)) {
                if (date.isAfter(LocalDate.now().minusYears(1))) {
                    count++;
                }
            }
        }
        return count >= 10;
    }



}
