package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.Models.ContractCustomer;

import java.util.List;

public interface ContractCustomerService {
    ContractCustomerDTO CmsToDTO(ContractCustomer ContractCustomer);
    ContractCustomer detailToCms(ContractCustomerDTO ContractCustomerDTO);
    void saveContractCustomer(ContractCustomerDTO cCustomer);
    List<ContractCustomerDTO> listSortedContractCustomers(String searchWord, String columnName, String sortingOrder);
    List<ContractCustomerDTO> allCustomers();
}
