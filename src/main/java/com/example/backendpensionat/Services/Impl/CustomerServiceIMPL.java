package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomerServiceIMPL implements CustomerService {
    BookingRepo bookingRepo;

    @Override
    public CustomerDTO customerToDTO(Customer customer) {
        return CustomerDTO.builder().id(customer.getId()).build();
    }

    @Override
    public CustomerDetailedDTO cDetailedToDTO(Customer customer) {
        return CustomerDetailedDTO.builder()
                .id(customer.getId())
                .firstName(customer
                        .getFirstName())
                .lastName(customer.getLastName()).email(customer.getEmail()).phone(customer.getPhone()).Ssn(customer.getSsn())
                .bookings(customer.getBookings().stream().map(b -> BookingDTO.builder().id(b.getId()).build()).toList()).build();
    }

    @Override
    public Customer detailToCustomer(CustomerDetailedDTO customerDetailedDTO) {
        return Customer.builder()
                .id(customerDetailedDTO.getId())
                .firstName(customerDetailedDTO.getFirstName())
                .lastName(customerDetailedDTO.getLastName())
                .email(customerDetailedDTO.getEmail())
                .phone(customerDetailedDTO.getPhone())
                .Ssn(customerDetailedDTO.getSsn())
                .bookings(customerDetailedDTO.getBookings().stream().map(b -> bookingRepo.findById(b.getId()).orElse(null)).toList()).build();
    }
}
