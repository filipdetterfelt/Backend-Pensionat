package com.example.backendpensionat.IntegrationTest;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.PropertiesConfigs.IntegrationPropertiesConfig;
import com.example.backendpensionat.Repos.ContractCustomerRepo;
import com.example.backendpensionat.Services.ContractCustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContractCustomerServiceIntegrationTest {

    @Autowired
    ContractCustomerRepo contractCustomerRepo;

    @Autowired
    ContractCustomerService sut;

    @Autowired
    IntegrationPropertiesConfig integrationPropertiesConfig;

    URL url;
    URL localUrl;

    @BeforeEach()
    void setUp() throws MalformedURLException {
        url = new URL(integrationPropertiesConfig.getContractCustomersUrl());
        localUrl = getClass().getClassLoader().getResource(integrationPropertiesConfig.getContractCustomersPathUrl());
    }


    @Test
    void getContractCustomersFromXMLTest() throws Exception {

        assertDoesNotThrow(() -> sut.getContractCustomersFromXML(localUrl));

        List<ContractCustomerDTO> contractCustomers = sut.getContractCustomersFromXML(localUrl);
        assertFalse(contractCustomers.isEmpty());
        assertNotNull(contractCustomers.get(0).externalId);
        assertNotNull(contractCustomers.get(0).companyName);
        assertNotNull(contractCustomers.get(0).contactName);
        assertNotNull(contractCustomers.get(0).contactTitle);
        assertNotNull(contractCustomers.get(0).streetAddress);
        assertNotNull(contractCustomers.get(0).postalCode);
        assertNotNull(contractCustomers.get(0).phone);
        assertNotNull(contractCustomers.get(0).country);
        assertNotNull(contractCustomers.get(0).fax);
        assertNotNull(contractCustomers.get(0).city);
        assertNull(contractCustomers.get(0).getInternalId());
    }

    @Test
    void getAndSaveContractCustomers() throws IOException {
        contractCustomerRepo.deleteAll();
        sut.getAndSaveContractCustomers(localUrl);

        assertEquals(3, contractCustomerRepo.count());
    }

}
