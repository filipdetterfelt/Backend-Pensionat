package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.ContractCustomerRepo;
import com.example.backendpensionat.Services.ContractCustomerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractCustomerServiceIMPL implements ContractCustomerService {

    @Autowired
    ContractCustomerRepo contractCustomerRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveContractCustomer(ContractCustomerDTO cCustomer) {
        contractCustomerRepo.save(detailToCms(cCustomer));
    }

    @Override
    public List<ContractCustomerDTO> listSortedContractCustomers(String searchWord, String columnName, String sortingOrder) {
        String jpqlQuery = String.format(
                "SELECT c FROM ContractCustomer c WHERE " +
                        "c.companyName LIKE :searchWord OR " +
                        "c.contactName LIKE :searchWord OR " +
                        "c.country LIKE :searchWord " +
                        "ORDER BY %s %s", columnName, sortingOrder);

        return entityManager.createQuery(jpqlQuery, ContractCustomer.class)
                .setParameter("searchWord", "%" + searchWord + "%")
                .getResultList().stream()
                .map(customer -> ContractCustomerDTO.builder()
                        .internalId(customer.getId())
                        .externalId(customer.getExternalId())
                        .companyName(customer.getCompanyName())
                        .contactName(customer.getContactName())
                        .contactTitle(customer.getContactTitle())
                        .streetAddress(customer.getStreetAddress())
                        .city(customer.getCity())
                        .postalCode(customer.getPostalCode())
                        .country(customer.getCountry())
                        .phone(customer.getPhone())
                        .fax(customer.getFax())
                        .build())
                .toList();
    }

    @Override
    public List<ContractCustomerDTO> allCustomers() {
        return contractCustomerRepo.findAll().stream().map(this::CmsToDTO).toList();
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
