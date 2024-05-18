package com.exampleCustomer.backendpensionat.UnitTest;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Repos.ContractCustomerRepo;
import com.example.backendpensionat.Services.ContractCustomerService;
import com.example.backendpensionat.Services.Impl.ContractCustomerServiceIMPL;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContractCustomerServiceUnitTest {
    private final ContractCustomerRepo contractCustomerRepo = mock(ContractCustomerRepo.class);
    URL localUrl = getClass().getClassLoader().getResource("./XmlJsonFiles/contractCustomers.xml");
    ContractCustomerService sut;

    @BeforeEach()
    void setUp() {
        sut = new ContractCustomerServiceIMPL(contractCustomerRepo);
    }

    @Test
    void whenGetContractCustomersShouldMapCorrectly() throws IOException {

        List<ContractCustomerDTO> result = sut.getContractCustomersFromXML(localUrl);

        assertEquals(3, result.size());
        ContractCustomerDTO exampleCustomer = result.get(0);
        assertEquals("Persson Kommanditbolag", exampleCustomer.companyName);
        assertEquals("Maria Åslund", exampleCustomer.contactName);
        assertEquals("gardener", exampleCustomer.contactTitle);
        assertEquals("Anderssons Gata 259", exampleCustomer.streetAddress);
        assertEquals("Kramland", exampleCustomer.city);
        assertEquals(60843, exampleCustomer.postalCode);
        assertEquals("Sverige", exampleCustomer.country);
        assertEquals("076-340-7143", exampleCustomer.phone);
        assertEquals("1500-16026", exampleCustomer.fax);
    }

    @Test
    void getAndSaveContractCustomersShouldInsertNewRecords() throws IOException {
        when(contractCustomerRepo.findContractCustomerByExternalId(Mockito.anyLong())).thenReturn(Optional.empty());

        sut.getAndSaveContractCustomers(true);

        verify(contractCustomerRepo, times(3)).save(argThat(customer -> customer.id == null));
    }

    @Test
    void getAndSaveContractCustomersShouldUpdateExistingRecords() throws IOException {
        ContractCustomer existingCustomer = new ContractCustomer();
        existingCustomer.id = 1L;
        existingCustomer.setExternalId(1L);

        when(contractCustomerRepo.findContractCustomerByExternalId(Mockito.anyLong())).thenReturn(Optional.empty());
        when(contractCustomerRepo.findContractCustomerByExternalId(1L)).thenReturn(Optional.of(existingCustomer));

        sut.getAndSaveContractCustomers(true);

        verify(contractCustomerRepo, times(2)).save(argThat(customer -> customer.externalId != 1L));
        verify(contractCustomerRepo, times(1)).save(argThat(customer -> customer.externalId == 1L));
    }

}
