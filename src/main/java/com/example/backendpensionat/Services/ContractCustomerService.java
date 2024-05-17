package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.DTO.ContractCustomerDetailedDTO;
import com.example.backendpensionat.Models.ContractCustomer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public interface ContractCustomerService {
    ContractCustomerDTO CmsToDTO(ContractCustomer ContractCustomer);
    ContractCustomer detailToCms(ContractCustomerDTO ContractCustomerDTO);
    List<ContractCustomerDTO> listSortedContractCustomers(String searchWord, String columnName, String sortingOrder);
    void saveContractCustomer(ContractCustomerDTO cCustomer);
    List<ContractCustomerDTO> listAllContractCustomers();
    ContractCustomer findcCustomerById(Long id);
    List<ContractCustomerDTO> getContractCustomersFromXML(URL url) throws IOException;
    void getAndSaveContractCustomers(Boolean isTest) throws IOException;

}

