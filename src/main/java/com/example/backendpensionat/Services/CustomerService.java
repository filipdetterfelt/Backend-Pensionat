package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Models.Customer;

import java.util.List;

public interface CustomerService {
    CustomerDTO customerToDTO(Customer customer);
    CustomerDetailedDTO cDetailedToDTO(Customer customer);
    Customer detailToCustomer(CustomerDetailedDTO customerDetailedDTO);
    List<CustomerDetailedDTO> listAllCustomers();
    String addCustomer(CustomerDetailedDTO customer);
    String removeCustomer(CustomerDetailedDTO customer);
    String changeCustomer(CustomerDetailedDTO customer);
    CustomerDetailedDTO findCustomerById(Long id);
    CustomerDTO findCustomerByBookingID(Long id);
}
