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
import org.springframework.ui.Model;

import java.util.List;


//@RequiredArgsConstructor
@Service
public class CustomerServiceIMPL implements CustomerService {
    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;

    public CustomerServiceIMPL(BookingRepo bookingRepo, CustomerRepo customerRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer addCustomer(CustomerDetailedDTO customer){
         return customerRepo.save(detailToCustomer(customer));
    }

    @Override
    public Customer changeCustomer(CustomerDetailedDTO customer) {
        return customerRepo.save(detailToCustomer(customer));
    }



    @Override
    public String removeCustomer(CustomerDetailedDTO customer){
        if (customer.getBookings() != null) {
            customerRepo.delete(detailToCustomer(customer));
            return "Customer has been removed";
        }
        return "Customer " + customer + " has not been removed";
    }

    @Override
    public List<CustomerDetailedDTO> listAllCustomers() {
        return customerRepo.findAll().stream().map(this::cDetailedToDTO).toList();
    }


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

    @Override
    public CustomerDetailedDTO findCustomerById(Long id){
        return cDetailedToDTO(customerRepo.findById(id).get());
    }

    @Override
    public CustomerDTO findCustomerByBookingID(Long id) {
        Customer customer = customerRepo.findAll()
                .stream()
                .filter(c -> c.getBookings()
                        .stream()
                        .anyMatch(b -> b.getId().equals(id)))
                .findFirst()
                .get();

        return customerToDTO(customer);
    }


}
