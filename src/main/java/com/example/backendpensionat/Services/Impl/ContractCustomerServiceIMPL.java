package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.*;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Repos.ContractCustomerRepo;
import com.example.backendpensionat.Services.ContractCustomerService;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractCustomerServiceIMPL implements ContractCustomerService {

    @Autowired
    ContractCustomerRepo contractCustomerRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ContractCustomerDTO> getContractCustomersFromXML(URL url) throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        ContractCustomerList contractCustomerList = xmlMapper.readValue(
                url, ContractCustomerList.class);

        return contractCustomerList.contractCustomerDTOList;
    }

    @Override
    public void getAndSaveContractCustomers(Boolean isTest) throws IOException {
        URL url;
        if (isTest) {
            url = getClass().getClassLoader().getResource("./XmlJsonFiles/contractCustomers.xml");
        } else {
            url = new URL("https://javaintegration.systementor.se/contractcustomers");
        }

        for(ContractCustomerDTO cc: getContractCustomersFromXML(url)) {
            Optional<ContractCustomer> contractCustomer = contractCustomerRepo.findContractCustomerByExternalId(cc.externalId);
            if(contractCustomer.isEmpty()) {
                contractCustomer = Optional.of(new ContractCustomer());
            }
            contractCustomer.get().setExternalId(cc.externalId);
            contractCustomer.get().setCompanyName(cc.companyName);
            contractCustomer.get().setContactName(cc.contactName);
            contractCustomer.get().setContactTitle(cc.contactTitle);
            contractCustomer.get().setStreetAddress(cc.streetAddress);
            contractCustomer.get().setCity(cc.city);
            contractCustomer.get().setPostalCode(cc.postalCode);
            contractCustomer.get().setPhone(cc.phone);
            contractCustomer.get().setFax(cc.fax);
            contractCustomerRepo.save(contractCustomer.get());
        }
    }

    @Override
    public void saveContractCustomer(ContractCustomerDTO cCustomer) {
        contractCustomerRepo.save(detailToCms(cCustomer));
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
