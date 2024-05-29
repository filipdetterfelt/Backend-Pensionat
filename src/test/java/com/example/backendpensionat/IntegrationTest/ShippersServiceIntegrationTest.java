package com.example.backendpensionat.IntegrationTest;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.PropertiesConfigs.IntegrationPropertiesConfig;
import com.example.backendpensionat.Repos.ShippersRepo;
import com.example.backendpensionat.Services.ShippersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShippersServiceIntegrationTest {

    @Autowired
    ShippersRepo shippersRepo;

    @Autowired
    ShippersService sut;

    @Autowired
    IntegrationPropertiesConfig integrationPropertiesConfig;

    @Test
    void getShippersFromJson() throws Exception {
        URL url = new URL(integrationPropertiesConfig.getShippersUrl());
        assertDoesNotThrow(() -> sut.getShippersFromJSON(url));

        List<ShippersDetailedDTO> shipper = sut.getShippersFromJSON(url);
        assertFalse(shipper.isEmpty());
        assertNull(shipper.get(0).internalId);
        assertNotNull(shipper.get(0).externalId);
        assertNotNull(shipper.get(0).email);
        assertNotNull(shipper.get(0).companyName);
        assertNotNull(shipper.get(0).contactName);
        assertNotNull(shipper.get(0).contactTitle);
        assertNotNull(shipper.get(0).streetAddress);
        assertNotNull(shipper.get(0).city);
        assertNotNull(shipper.get(0).postalCode);
        assertNotNull(shipper.get(0).country);
        assertNotNull(shipper.get(0).phone);
        assertNotNull(shipper.get(0).fax);
    }

    @Test
    void getAndSaveContractCustomers() throws IOException {
        shippersRepo.deleteAll();
        sut.getAndSaveShippers(integrationPropertiesConfig.getLocalPathShippers());

        assertEquals(3, shippersRepo.count());
    }

}