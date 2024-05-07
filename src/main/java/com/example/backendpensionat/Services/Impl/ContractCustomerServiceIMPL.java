package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Services.ContractCustomerService;
import org.springframework.stereotype.Service;

@Service
public class ContractCustomerServiceIMPL implements ContractCustomerService {


    @Override
    public ContractCustomer saveContractCustomer(ContractCustomerDTO cCustomer) {
        return contractCustomerRepo.save(detailToCms(cCustomer));
    }

    public ContractCustomerDTO CmsToDTO(ContractCustomer ContractCustomer) {
        return ContractCustomerDTO.builder()
                .externalId(ContractCustomer.externalId)
                .companyName(ContractCustomer.companyName)
                .contactName(ContractCustomer.contactName)
                .contactTitle(ContractCustomer.contactTitle)
                .streetAddress(ContractCustomer.streetAddress)
                .city(ContractCustomer.city)
                .postalCode(ContractCustomer.postalCode)
                .country(ContractCustomer.country)
                .phone(ContractCustomer.phone)
                .fax(ContractCustomer.fax).build();
    }

    public ContractCustomer detailToCms(ContractCustomerDTO ContractCustomerDTO) {
        return ContractCustomer.builder()
                .externalId(ContractCustomerDTO.externalId)
                .companyName(ContractCustomerDTO.companyName)
                .contactName(ContractCustomerDTO.contactName)
                .streetAddress(ContractCustomerDTO.streetAddress)
                .city(ContractCustomerDTO.city)
                .postalCode(ContractCustomerDTO.postalCode)
                .country(ContractCustomerDTO.country)
                .phone(ContractCustomerDTO.phone)
                .fax(ContractCustomerDTO.fax).build();
    }
}
