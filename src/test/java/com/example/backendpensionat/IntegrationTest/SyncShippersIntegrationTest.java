package com.example.backendpensionat.IntegrationTest;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Services.ShippersService;
import com.example.backendpensionat.SyncShippers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SyncShippersIntegrationTest {

    @Autowired
    private SyncShippers syncShippers;

    @MockBean
    private ShippersService shippersService;

    @MockBean
    private ObjectMapper objectMapper;

    @Test
    public void testRun() throws Exception {
        ShippersDetailedDTO mockShipper = new ShippersDetailedDTO();
        List<ShippersDetailedDTO> mockShippersList = Collections.singletonList(mockShipper);

        when(objectMapper.readValue(any(File.class), any())).thenReturn(mockShippersList);

        syncShippers.run();

        verify(shippersService, atLeastOnce()).saveShipper(any());
    }

}