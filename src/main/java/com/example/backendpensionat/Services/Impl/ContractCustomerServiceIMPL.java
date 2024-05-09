package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.DTO.ContractCustomerDetailedDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Repos.ContractCustomerRepo;
import com.example.backendpensionat.Services.ContractCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractCustomerServiceIMPL implements ContractCustomerService {

    @Autowired
    ContractCustomerRepo contractCustomerRepo;

    @Override
    public ContractCustomer saveContractCustomer(ContractCustomerDTO cCustomer) {
        return contractCustomerRepo.save(detailToCms(cCustomer));
    }

    @Override
    public List<ContractCustomerDTO> listAllContractCustomers() {
        return contractCustomerRepo.findAll()
                .stream()
                .map(this::CmsToDTO)
                .distinct()
                .collect(Collectors.toList());




    }


    @Override
    public String removeCCustomer(ContractCustomerDetailedDTO cCustomer) {
        /*if(cCustomer.getBookings() != null){
            contractCustomerRepo.delete();
        }*/
        return "";
    }

    @Override
    public ContractCustomer findcCustomerById(Long id) {
        ContractCustomer cC = contractCustomerRepo.findById(id).get();

        ContractCustomerDetailedDTO cCD = dtoToDetailedcCustomer(cC);
        return cC;
    }



   /* public ContractCustomerDTO cCToDto(ContractCustomer cCustomer) {
        List<ContractCustomer> contractCustomerList = contractCustomerRepo.findAll();
        ContractCustomer matchcCustomer = contractCustomerList.stream()
                .filter(contractCustomer -> contractCustomer.externalId
                        .equals(contractCustomer.externalId)).findFirst()
                .orElse(new ContractCustomer());

        return ContractCustomer.builder()
                .internalId(cCustomer.id)
                .companyName(matchcCustomer.companyName)
                .contactName(matchcCustomer.contactName)
                .country(matchcCustomer.country).build();
    }*/

    public ContractCustomerDetailedDTO dtoToDetailedcCustomer(ContractCustomer cC){
        return ContractCustomerDetailedDTO.builder()
                .externalId(cC.externalId)
                .companyName(cC.companyName)
                .contactName(cC.contactName)
                .contactTitle(cC.contactTitle)
                .streetAddress(cC.streetAddress)
                .city(cC.city)
                .postalCode(cC.postalCode)
                .country(cC.country)
                .phone(cC.phone)
                .fax(cC.fax).build();

    }


    public ContractCustomerDTO CmsToDTO(ContractCustomer ContractCustomer) {
        return ContractCustomerDTO.builder()
                .internalId(ContractCustomer.id)
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

    public ContractCustomer detailToCms(ContractCustomerDTO contractCustomerDTO) {
        List<ContractCustomer> contractCustomers = contractCustomerRepo.findAll();

        ContractCustomer matchingCustomer = contractCustomers.stream().filter(customer ->
                customer.externalId.equals(contractCustomerDTO.externalId)).findFirst().orElse(new ContractCustomer());

            return ContractCustomer.builder()
                    .id(matchingCustomer.id)
                    .externalId(contractCustomerDTO.externalId)
                    .companyName(contractCustomerDTO.companyName)
                    .contactName(contractCustomerDTO.contactName)
                    .contactTitle(contractCustomerDTO.contactTitle)
                    .streetAddress(contractCustomerDTO.streetAddress)
                    .city(contractCustomerDTO.city)
                    .postalCode(contractCustomerDTO.postalCode)
                    .country(contractCustomerDTO.country)
                    .phone(contractCustomerDTO.phone)
                    .fax(contractCustomerDTO.fax).build();
    }
}
