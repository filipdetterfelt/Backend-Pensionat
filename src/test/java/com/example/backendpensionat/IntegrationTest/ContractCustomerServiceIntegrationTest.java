package com.example.backendpensionat.IntegrationTest;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.PropertiesConfigs.IntegrationPropertiesConfig;
import com.example.backendpensionat.Repos.ContractCustomerRepo;
import com.example.backendpensionat.Services.ContractCustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
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

    @Test
    void getContractCustomersFromXMLTest() throws Exception {
        URL url = new URL(integrationPropertiesConfig.getContractCustomersUrl());
        assertDoesNotThrow(() -> sut.getContractCustomersFromXML(url));

        List<ContractCustomerDTO> contractCustomers = sut.getContractCustomersFromXML(url);
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
        sut.getAndSaveContractCustomers(true);

        assertEquals(3, contractCustomerRepo.count());
    }

}
