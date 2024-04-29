package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Models.Customer;

import java.util.List;

public interface CustomerService {
    CustomerDTO customerToDTO(Customer customer);
    CustomerDetailedDTO cDetailedToDTO(Customer customer);
    Customer detailToCustomer(CustomerDetailedDTO customerDetailedDTO);
    public List<CustomerDetailedDTO> listAllCustomers();
}
