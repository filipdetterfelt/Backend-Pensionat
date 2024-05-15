package com.example.backendpensionat.IntegrationTest;

import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.SyncContractCustomers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SyncContractCustomersIntegrationTest{

    @Autowired
    private SyncContractCustomers syncContractCustomers;

    @MockBean
    private ContractCustomer contractCustomer;

    @Test
    public void syncContractCustomerIntegrationTest() throws Exception {
        SyncContractCustomers syncContractCustomers = mock(SyncContractCustomers.class);
        when(syncContractCustomers).thenReturn(syncContractCustomers);



        syncContractCustomers.run();

        verify(contractCustomer, times(1));
    }

}
