package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Models.Customer;

public interface ContractCustomerService {
    ContractCustomerDTO CmsToDTO(ContractCustomer ContractCustomer);
    ContractCustomer detailToCms(ContractCustomerDTO ContractCustomerDTO);
}
